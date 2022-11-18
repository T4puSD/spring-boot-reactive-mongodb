package com.tapusd.springbootreactivemongo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ActionResponse(boolean result, String message, Object data) {
}
