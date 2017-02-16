package com.jeppesen.nimbus.rest;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RestHandler<T> {

    private Logger logger;

    private final Class typeParameterClass;

    private String baseUriRestApi;
    private HttpAuthenticationFeature feature;

    private Map<String, String> queryParams;

    public RestHandler(Class typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        setLogger(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME));
    }

    public RestHandler baseUri(String baseUri) {

        this.baseUriRestApi = baseUri;
        return this;
    }

    public RestHandler basicAuthCredentials(String username, String password) {
        this.feature = HttpAuthenticationFeature
                .basicBuilder()
                .credentials(username, password)
                .build();

        return this;
    }

    public RestHandler assignLogger(Logger logger) {
        setLogger(logger);
        return this;
    }

    private void setLogger(Logger logger) {
        if (logger == null) {
            String message = "The submitted logger is null. Please make sure to pass a correctly instantiated Logger. Will use default console logger instead";
            this.logger.warning(String.format("********** %s ***********", message));
        }

        this.logger = logger;
    }

    public void addQueryParam(String key, String value) {
        if (queryParams == null) {
            queryParams = new HashMap<String, String>();
        }

        queryParams.put(key, value);
    }

    public List<T> getList(String path, String acceptHeader) throws Exception {

        List<T> list = new ArrayList<T>();

        try {
            Client client = createClient();

            Response response = client.target(baseUriRestApi)
                    .path(path)
                    .request(acceptHeader)
                    .get();

            if (response.getStatus() > 299) {
                handleErrorResponse(response);
            } else {

                GenericType<List<T>> genericType = getListGenericType();
                list = response.readEntity(genericType);
            }

        } catch (RuntimeException r) {
            handleRuntimeException(r);
        } catch (Exception e) {
            handleException(e);
        }

        return list;
    }

    public T get(String path, String acceptHeader) throws Exception {

        T object = null;

        try {
            Client client = createClient();

            WebTarget webTarget = client.target(baseUriRestApi);
            webTarget = prepareQueryParams(webTarget);

            Response response = webTarget
                    .path(path)
                    .request(acceptHeader)
                    .get();

            if (response.getStatus() > 299) {
                handleErrorResponse(response);
            } else {

                GenericType<T> genericType = getGenericType();
                object = response.readEntity(genericType);
            }

        } catch (RuntimeException r) {
            handleRuntimeException(r);
        } catch (Exception e) {
            handleException(e);
        }

        return object;
    }

    public T post(String path, String acceptHeader, String contentTypeHeader, Object body) throws Exception {

        T object = null;

        try {
            Client client = createClient();

            WebTarget webTarget = client.target(baseUriRestApi);
            webTarget = prepareQueryParams(webTarget);

            Response response = webTarget
                    .path(path)
                    .request(acceptHeader)
                    .post(Entity.entity(body, contentTypeHeader));

            if (response.getStatus() > 299) {
                handleErrorResponse(response);
            } else {

                GenericType<T> genericType = getGenericType();
                object = response.readEntity(genericType);
            }

        } catch (RuntimeException r) {
            handleRuntimeException(r);
        } catch (Exception e) {
            handleException(e);
        }

        return object;
    }

    public T put(String path, String acceptHeader, String contentTypeHeader, Object body) throws Exception {

        GenericType<T> genericType = getGenericType();

        T object = null;

        try {
            Client client = createClient();

            WebTarget webTarget = client.target(baseUriRestApi);
            webTarget = prepareQueryParams(webTarget);

            object = webTarget
                    .path(path)
                    .request(acceptHeader)
                    .put(Entity.entity(body, contentTypeHeader), genericType);

        } catch (RuntimeException r) {
            handleRuntimeException(r);
        } catch (Exception e) {
            handleException(e);
        }

        return object;
    }

    public void delete(String path) throws Exception {


        try {
            Client client = createClient();

            WebTarget webTarget = client.target(baseUriRestApi);
            webTarget = prepareQueryParams(webTarget);

            Response response = webTarget
                    .path(path)
                    .request()
                    .delete();

            if (response.getStatus() > 299) {
                handleErrorResponse(response);
            }

        } catch (RuntimeException r) {
            handleRuntimeException(r);
        } catch (Exception e) {
            handleException(e);
        }
    }

    private Client createClient() throws IOException {
        Client client = ClientBuilder.newClient();

        client.register(new LoggingFilter(logger, true));

        if (feature != null) {
            client.register(feature);
        }

        return client;
    }


    private WebTarget prepareQueryParams(WebTarget webTarget) {

        if (webTarget == null) {
            throw new IllegalArgumentException("The incoming WebTarget was null");
        }

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return webTarget;
    }

    private void handleErrorResponse(Response response) throws RestErrorException {

        RestErrorHandler.handleErrorResponse(response);
    }

    private void handleException(Exception e) throws Exception {

        this.logger.severe(e.getMessage());
        throw e;
    }

    private void handleRuntimeException(RuntimeException r) {
        this.logger.severe(r.getMessage());
        throw r;
    }

    private GenericType<List<T>> getListGenericType() {
        return new GenericType<List<T>>(new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{typeParameterClass};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        });
    }

    private GenericType<T> getGenericType() {
        return new GenericType<T>(new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{typeParameterClass};
            }

            public Type getRawType() {
                return Type.class;
            }

            public Type getOwnerType() {
                return Type.class;
            }
        });
    }
}
