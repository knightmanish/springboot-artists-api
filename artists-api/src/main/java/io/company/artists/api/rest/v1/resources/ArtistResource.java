package io.company.artists.api.rest.v1.resources;

import io.company.artists.api.lib.Artist;
import io.company.artists.api.lib.RestError;
import io.company.artists.api.services.ArtistService;
import io.company.artists.api.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/artists")
@SecurityRequirement(name = "artistssecurity")
public class ArtistResource {

    private final ArtistService artistService;

    @Autowired
    public ArtistResource(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(description = "Returns a list of artists", security = {
            @SecurityRequirement(name = "ApiKeyAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of artists, optional limited size and sorted", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Artist.class)))),
            @ApiResponse(responseCode = "400", description = "HTTP 400 Bad Request / Validation Error - The server cannot or will not process the request due to an apparent client error (e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "401", description = "HTTP 401 Unauthorized  - Authentication credentials are required to the resource. All requests must be authenticated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "403", description = "HTTP 403 Forbidden - The request was valid, but the server is refusing action. The user might not have the necessary permissions for a resource, or may need an account of some sort.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "404", description = "HTTP 404 Not found - We could not locate the resource based on the specified URL. The requested resource could not be found but may be available in the future. Subsequent requests by the client are permissible.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "500", description = "HTTP 500 Internal Server Error  - A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "503", description = "HTTP 503 - The server is currently unavailable (because it is overloaded or down for maintenance). Generally, this is a temporary state.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)))})
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Artist>> getArtists(
            @Parameter(in = ParameterIn.QUERY, description = "The page to be shown", schema = @Schema())
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @Parameter(in = ParameterIn.QUERY, description = "Limits the number of items on a page", schema = @Schema())
            @RequestParam(defaultValue = "10", required = false) Integer pageLimit,
            @Parameter(in = ParameterIn.QUERY, description = "Order by the property ( a field in Artist )", schema = @Schema())
            @RequestParam(defaultValue = "artistName", value = "sortField", required = false) String sortField,
            @Parameter(in = ParameterIn.QUERY, description = "Sorting Order for the property", schema = @Schema())
            @RequestParam(value = "order", required = false) Sort.Direction sortingOrder) {

        Pageable pageable = createPageable(page, pageLimit, sortField, sortingOrder);

        List<Artist> artists = artistService.findArtists(pageable);
        return ResponseEntity.ok().header("X-Total-Count", artistService.findArtistsCount().toString()).body(artists);
    }



    @Operation(description = "Obtain information about an artist from his or her unique username", security = {
            @SecurityRequirement(name = "ApiKeyAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned an artist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))),
            @ApiResponse(responseCode = "400", description = "HTTP 400 Bad Request / Validation Error - The server cannot or will not process the request due to an apparent client error (e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "401", description = "HTTP 401 Unauthorized  - Authentication credentials are required to the resource. All requests must be authenticated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "403", description = "HTTP 403 Forbidden - The request was valid, but the server is refusing action. The user might not have the necessary permissions for a resource, or may need an account of some sort.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "404", description = "HTTP 404 Not found - We could not locate the resource based on the specified URL. The requested resource could not be found but may be available in the future. Subsequent requests by the client are permissible.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "500", description = "HTTP 500 Internal Server Error  - A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "503", description = "HTTP 503 - The server is currently unavailable (because it is overloaded or down for maintenance). Generally, this is a temporary state.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)))})
    @GetMapping(value = "/{username}", produces = {"application/json"})
    public ResponseEntity<Artist> getArtist(@Parameter(in = ParameterIn.PATH, required = true, schema = @Schema()) @PathVariable String username) throws ResourceNotFoundException {

        Artist artist = artistService.findArtistByUsername(username);
        return ResponseEntity.ok(artist);
    }

    @Operation(description = "Lets a user post a new artist", security = {
            @SecurityRequirement(name = "ApiKeyAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully created a new artist (list)"),
            @ApiResponse(responseCode = "400", description = "HTTP 400 Bad Request / Validation Error - The server cannot or will not process the request due to an apparent client error (e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "401", description = "HTTP 401 Unauthorized  - Authentication credentials are required to the resource. All requests must be authenticated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "403", description = "HTTP 403 Forbidden - The request was valid, but the server is refusing action. The user might not have the necessary permissions for a resource, or may need an account of some sort.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "404", description = "HTTP 404 Not found - We could not locate the resource based on the specified URL. The requested resource could not be found but may be available in the future. Subsequent requests by the client are permissible.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "500", description = "HTTP 500 Internal Server Error  - A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class))),
            @ApiResponse(responseCode = "503", description = "HTTP 503 - The server is currently unavailable (because it is overloaded or down for maintenance). Generally, this is a temporary state.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestError.class)))}
    )
    @PostMapping(
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity createArtist(@Valid @RequestBody List<Artist> artists) {

        List<Artist> artist = artistService.createArtists(artists);
        List<String> locations = artist.stream().map(this::createUri).collect(Collectors.toList());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.addAll(HttpHeaders.LOCATION, locations);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    private String createUri(Artist artist) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(artist.getUsername())
                .toUri()
                .toString();
    }

    private Pageable createPageable(Integer page, Integer pageLimit, String sortField, Sort.Direction sortingOrder) {
        Sort.Order oder;
        if (sortingOrder == Sort.Direction.DESC) {
            oder = Sort.Order.desc(sortField);
        } else {
            oder = Sort.Order.asc(sortField);
        }
        oder.ignoreCase();
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(oder);

        Sort sort = Sort.by(orders);
        return PageRequest.of(page, pageLimit, sort);
    }

}
