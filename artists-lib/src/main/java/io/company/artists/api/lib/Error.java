package io.company.artists.api.lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Error {
    @JsonProperty("code")
    private String code = null;

    @JsonProperty("title")
    private String title = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("docUrl")
    private String docUrl = null;

    public Error code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Internal error code for ambiguous status codes
     * @return code
     **/
    @Schema(example = "E-40010", required = true, description = "Internal error code for ambiguous status codes")
    @NotNull

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Error title(String title) {
        this.title = title;
        return this;
    }

    /**
     * title of the error, e.g. to show in the error box title bar
     * @return title
     **/
    @Schema(example = "ID not found", description = "title of the error, e.g. to show in the error box title bar")

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Error message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Human readable error
     * @return message
     **/
    @Schema(example = "The requested ID could not be found on our systems. Please verify its correctness or call service personell.", required = true, description = "Human readable error")
    @NotNull

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error docUrl(String docUrl) {
        this.docUrl = docUrl;
        return this;
    }

    /**
     * Documentation reference
     * @return docUrl
     **/
    @Schema(example = "https://example.org", description = "Documentation reference")

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Error error = (Error) o;
        return Objects.equals(this.code, error.code) &&
                Objects.equals(this.title, error.title) &&
                Objects.equals(this.message, error.message) &&
                Objects.equals(this.docUrl, error.docUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, message, docUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    docUrl: ").append(toIndentedString(docUrl)).append("\n");
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
