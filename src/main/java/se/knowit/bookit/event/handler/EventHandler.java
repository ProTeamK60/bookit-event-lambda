package se.knowit.bookit.event.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import se.knowit.bookit.event.dto.EventDTO;
import se.knowit.bookit.event.dto.EventMapper;
import se.knowit.bookit.event.model.Event;
import se.knowit.bookit.event.service.EventService;
import se.knowit.bookit.event.service.EventServiceImpl;
import se.knowit.bookit.gatewayproxy.ApiGatewayProxyRequest;
import se.knowit.bookit.gatewayproxy.ApiGatewayProxyResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventHandler {

    private final static String BASE_PATH = "https://jwuqkp23xj.execute-api.eu-north-1.amazonaws.com";
    private final EventService service;
    private final EventMapper mapper;

    public EventHandler() {
        service = new EventServiceImpl();
        mapper = new EventMapper();
    }

    public ApiGatewayProxyResponse getEvent(ApiGatewayProxyRequest request, Context context) throws JsonProcessingException {
        String eventId = request.getQueryStringParameters().get("eventId");
        Event event = service.findByEventId(UUID.fromString(eventId)).orElseThrow();
        return buildResponse().withStatusCode(200).withBody(mapper.toDto(event));
    }

    public ApiGatewayProxyResponse saveEvent(ApiGatewayProxyRequest request, Context context) throws JsonProcessingException {
        Event incomingEvent = mapper.fromDto(EventDTO.fromJson(request.getBody()));
        Event savedEvent = service.saveEvent(incomingEvent);
        Map<String,String> headers = new HashMap<>();
        headers.put("Location", getUrl(request) + "?eventId=" + savedEvent.getEventId());
        return buildResponse(headers).withStatusCode(201);
    }

    private ApiGatewayProxyResponse buildResponse() {
        ApiGatewayProxyResponse response = new ApiGatewayProxyResponse();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);
        return response;
    }

    private ApiGatewayProxyResponse buildResponse(Map<String,String> headers) {
        ApiGatewayProxyResponse response = buildResponse();
        response.getHeaders().putAll(headers);
        return response;
    }

    private String getUrl(ApiGatewayProxyRequest request) {
        return BASE_PATH + request.getPath() + request.getResource();
    }
}
