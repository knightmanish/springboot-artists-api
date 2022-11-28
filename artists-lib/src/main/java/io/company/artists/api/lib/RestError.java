package io.company.artists.api.lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestError {

    @JsonProperty("status")
    private Long status = null;

    @JsonProperty("method")
    private String method = null;

    @JsonProperty("correlation_id")
    private String correlationId = null;

    @JsonProperty("request_query")
    private String requestQuery = null;

    @JsonProperty("request_URI")
    private String requestURI = null;

    @JsonProperty("errors")
    @Valid
    private List<Error> errors = null;

    public RestError status(Long status) {
        this.status = status;
        return this;
    }

    /**
     * The corresponding HTTP status code.
     * @return status
     **/
    @Schema(required = true, description = "The corresponding HTTP status code.")
    @NotNull

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public RestError method(String method) {
        this.method = method;
        return this;
    }

    /**
     * The corresponding HTTP Method (GET,POST,...)
     * @return method
     **/
    @Schema(description = "The corresponding HTTP Method (GET,POST,...)")

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RestError correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    /**
     * the generated Id, that is used for backend logging
     * @return correlationId
     **/
    @Schema(description = "the generated Id, that is used for backend logging")

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public RestError requestQuery(String requestQuery) {
        this.requestQuery = requestQuery;
        return this;
    }

    /**
     * the query as string
     * @return requestQuery
     **/
    @Schema(description = "the query as string")

    public String getRequestQuery() {
        return requestQuery;
    }

    public void setRequestQuery(String requestQuery) {
        this.requestQuery = requestQuery;
    }

    public RestError requestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    /**
     * the requested URI
     * @return requestURI
     **/
    @Schema(description = "the requested URI")

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public RestError errors(List<Error> errors) {
        this.errors = errors;
        return this;
    }

    public RestError addErrorsItem(Error errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<Error>();
        }
        this.errors.add(errorsItem);
        return this;
    }

    /**
     * Get errors
     * @return errors
     **/
    @Valid
    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestError restError = (RestError) o;
        return Objects.equals(this.status, restError.status) &&
                Objects.equals(this.method, restError.method) &&
                Objects.equals(this.correlationId, restError.correlationId) &&
                Objects.equals(this.requestQuery, restError.requestQuery) &&
                Objects.equals(this.requestURI, restError.requestURI) &&
                Objects.equals(this.errors, restError.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, method, correlationId, requestQuery, requestURI, errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RestError {\n");

        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    method: ").append(toIndentedString(method)).append("\n");
        sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
        sb.append("    requestQuery: ").append(toIndentedString(requestQuery)).append("\n");
        sb.append("    requestURI: ").append(toIndentedString(requestURI)).append("\n");
        sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
