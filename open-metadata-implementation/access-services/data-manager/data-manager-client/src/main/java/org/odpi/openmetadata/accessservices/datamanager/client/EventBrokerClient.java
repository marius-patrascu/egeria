/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

package org.odpi.openmetadata.accessservices.datamanager.client;

import org.odpi.openmetadata.accessservices.datamanager.api.EventBrokerInterface;
import org.odpi.openmetadata.accessservices.datamanager.client.rest.DataManagerRESTClient;
import org.odpi.openmetadata.accessservices.datamanager.metadataelements.*;
import org.odpi.openmetadata.accessservices.datamanager.properties.*;
import org.odpi.openmetadata.accessservices.datamanager.rest.*;
import org.odpi.openmetadata.commonservices.ffdc.rest.GUIDResponse;
import org.odpi.openmetadata.frameworks.auditlog.AuditLog;
import org.odpi.openmetadata.frameworks.connectors.ffdc.InvalidParameterException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException;

import java.util.List;

/**
 * EventBrokerClient is the client for managing topics from an Event Manager.
 */
public class EventBrokerClient extends SchemaManagerClient implements EventBrokerInterface
{
    private final String eventBrokerGUIDParameterName = "eventBrokerGUID";
    private final String eventBrokerNameParameterName = "eventBrokerName";
    private final String editURLTemplatePrefix = "/servers/{0}/open-metadata/access-services/data-manager/users/{1}/event-brokers/{2}/{3}/topics";
    private final String retrieveURLTemplatePrefix   = "/servers/{0}/open-metadata/access-services/data-manager/users/{1}/topics";
    private final String governanceURLTemplatePrefix = "/servers/{0}/open-metadata/access-services/data-manager/users/{1}/topics";


    /**
     * Create a new client with no authentication embedded in the HTTP request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param auditLog logging destination
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public EventBrokerClient(String   serverName,
                             String   serverPlatformURLRoot,
                             AuditLog auditLog) throws InvalidParameterException
    {
        super(serverName, serverPlatformURLRoot, auditLog);
    }


    /**
     * Create a new client with no authentication embedded in the HTTP request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public EventBrokerClient(String serverName,
                             String serverPlatformURLRoot) throws InvalidParameterException
    {
        super(serverName, serverPlatformURLRoot);
    }


    /**
     * Create a new client that passes userId and password in each HTTP request.  This is the
     * userId/password of the calling server.  The end user's userId is sent on each request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param userId caller's userId embedded in all HTTP requests
     * @param password caller's userId embedded in all HTTP requests
     * @param auditLog logging destination
     *
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public EventBrokerClient(String   serverName,
                             String   serverPlatformURLRoot,
                             String   userId,
                             String   password,
                             AuditLog auditLog) throws InvalidParameterException
    {
        super(serverName, serverPlatformURLRoot, userId, password, auditLog);
    }


    /**
     * Create a new client that is going to be used in an OMAG Server.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param restClient client that issues the REST API calls
     * @param maxPageSize maximum number of results supported by this server
     * @param auditLog logging destination
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public EventBrokerClient(String                serverName,
                             String                serverPlatformURLRoot,
                             DataManagerRESTClient restClient,
                             int                   maxPageSize,
                             AuditLog              auditLog) throws InvalidParameterException
    {
        super(serverName, serverPlatformURLRoot, restClient, maxPageSize, auditLog);
    }


    /**
     * Create a new client that passes userId and password in each HTTP request.  This is the
     * userId/password of the calling server.  The end user's userId is sent on each request.
     *
     * @param serverName name of the server to connect to
     * @param serverPlatformURLRoot the network address of the server running the OMAS REST servers
     * @param userId caller's userId embedded in all HTTP requests
     * @param password caller's userId embedded in all HTTP requests
     * @throws InvalidParameterException there is a problem creating the client-side components to issue any
     * REST API calls.
     */
    public EventBrokerClient(String serverName,
                             String serverPlatformURLRoot,
                             String userId,
                             String password) throws InvalidParameterException
    {
        super(serverName, serverPlatformURLRoot, userId, password);
    }


    /* ========================================================
     * The topic is the top level asset on an event manager server
     */


    /**
     * Create a new metadata element to represent a topic.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param topicProperties properties to store
     *
     * @return unique identifier of the new metadata element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public String createTopic(String          userId,
                              String          eventBrokerGUID,
                              String          eventBrokerName,
                              TopicProperties topicProperties) throws InvalidParameterException,
                                                                      UserNotAuthorizedException,
                                                                      PropertyServerException
    {
        final String methodName                  = "createTopic";
        final String propertiesParameterName     = "topicProperties";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateObject(topicProperties, propertiesParameterName, methodName);
        invalidParameterHandler.validateName(topicProperties.getQualifiedName(), qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix;

        GUIDResponse restResult = restClient.callGUIDPostRESTCall(methodName,
                                                                  urlTemplate,
                                                                  topicProperties,
                                                                  serverName,
                                                                  userId,
                                                                  eventBrokerGUID,
                                                                  eventBrokerName);

        return restResult.getGUID();
    }


    /**
     * Create a new metadata element to represent a topic using an existing metadata element as a template.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param templateGUID unique identifier of the metadata element to copy
     * @param templateProperties properties that override the template
     *
     * @return unique identifier of the new metadata element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public String createTopicFromTemplate(String             userId,
                                          String             eventBrokerGUID,
                                          String             eventBrokerName,
                                          String             templateGUID,
                                          TemplateProperties templateProperties) throws InvalidParameterException,
                                                                                        UserNotAuthorizedException,
                                                                                        PropertyServerException
    {
        final String methodName                  = "createTopicFromTemplate";
        final String templateGUIDParameterName   = "templateGUID";
        final String propertiesParameterName     = "templateProperties";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(templateGUID, templateGUIDParameterName, methodName);
        invalidParameterHandler.validateObject(templateProperties, propertiesParameterName, methodName);
        invalidParameterHandler.validateName(templateProperties.getQualifiedName(), qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/from-template/{4}";

        GUIDResponse restResult = restClient.callGUIDPostRESTCall(methodName,
                                                                  urlTemplate,
                                                                  templateProperties,
                                                                  serverName,
                                                                  userId,
                                                                  eventBrokerGUID,
                                                                  eventBrokerName,
                                                                  templateGUID);

        return restResult.getGUID();
    }


    /**
     * Update the metadata element representing a topic.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param topicGUID unique identifier of the metadata element to update
     * @param isMergeUpdate are unspecified properties unchanged (true) or removed?
     * @param topicProperties new properties for this element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void updateTopic(String          userId,
                            String          eventBrokerGUID,
                            String          eventBrokerName,
                            String          topicGUID,
                            boolean         isMergeUpdate,
                            TopicProperties topicProperties) throws InvalidParameterException,
                                                                    UserNotAuthorizedException,
                                                                    PropertyServerException
    {
        final String methodName                  = "updateTopic";
        final String elementGUIDParameterName    = "topicGUID";
        final String propertiesParameterName     = "topicProperties";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(topicGUID, elementGUIDParameterName, methodName);
        invalidParameterHandler.validateObject(topicProperties, propertiesParameterName, methodName);
        invalidParameterHandler.validateName(topicProperties.getQualifiedName(), qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/{4}?isMergeUpdate={5}";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        topicProperties,
                                        serverName,
                                        userId,
                                        eventBrokerGUID,
                                        eventBrokerName,
                                        topicGUID,
                                        isMergeUpdate);
    }


    /**
     * Update the zones for the topic asset so that it becomes visible to consumers.
     * (The zones are set to the list of zones in the publishedZones option configured for each
     * instance of the Data Manager OMAS).
     *
     * @param userId calling user
     * @param topicGUID unique identifier of the metadata element to publish
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void publishTopic(String userId,
                             String topicGUID) throws InvalidParameterException,
                                                      UserNotAuthorizedException,
                                                      PropertyServerException
    {
        final String methodName               = "publishTopic";
        final String elementGUIDParameterName = "topicGUID";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(topicGUID, elementGUIDParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + governanceURLTemplatePrefix + "/{4}/publish";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        nullRequestBody,
                                        serverName,
                                        userId,
                                        topicGUID);
    }


    /**
     * Update the zones for the topic asset so that it is no longer visible to consumers.
     * (The zones are set to the list of zones in the defaultZones option configured for each
     * instance of the Data Manager OMAS.  This is the setting when the topic is first created).
     *
     * @param userId calling user
     * @param topicGUID unique identifier of the metadata element to withdraw
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void withdrawTopic(String userId,
                              String topicGUID) throws InvalidParameterException,
                                                       UserNotAuthorizedException,
                                                       PropertyServerException
    {
        final String methodName               = "withdrawTopic";
        final String elementGUIDParameterName = "topicGUID";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(topicGUID, elementGUIDParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + governanceURLTemplatePrefix + "topics/{4}/withdraw";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        nullRequestBody,
                                        serverName,
                                        userId,
                                        topicGUID);
    }


    /**
     * Remove the metadata element representing a topic.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param topicGUID unique identifier of the metadata element to remove
     * @param qualifiedName unique name of the metadata element to remove
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void removeTopic(String userId,
                            String eventBrokerGUID,
                            String eventBrokerName,
                            String topicGUID,
                            String qualifiedName) throws InvalidParameterException,
                                                         UserNotAuthorizedException,
                                                         PropertyServerException
    {
        final String methodName = "removeTopic";
        final String eventBrokerGUIDParameterName = "eventBrokerGUID";
        final String eventBrokerNameParameterName = "eventBrokerName";
        final String elementGUIDParameterName    = "topicGUID";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(topicGUID, elementGUIDParameterName, methodName);
        invalidParameterHandler.validateName(qualifiedName, qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/{4}/{5}/delete";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        nullRequestBody,
                                        serverName,
                                        userId,
                                        eventBrokerGUID,
                                        eventBrokerName,
                                        topicGUID,
                                        qualifiedName);
    }


    /**
     * Retrieve the list of topic metadata elements that contain the search string.
     * The search string is treated as a regular expression.
     *
     * @param userId calling user
     * @param searchString string to find in the properties
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of matching metadata elements
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<TopicElement> findTopics(String userId,
                                         String searchString,
                                         int    startFrom,
                                         int    pageSize) throws InvalidParameterException,
                                                                 UserNotAuthorizedException,
                                                                 PropertyServerException
    {
        final String methodName                = "findTopics";
        final String searchStringParameterName = "searchString";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateSearchString(searchString, searchStringParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/by-search-string/{2}?startFrom={3}&pageSize={4}";

        TopicsResponse restResult = restClient.callTopicsGetRESTCall(methodName,
                                                                     urlTemplate,
                                                                     serverName,
                                                                     userId,
                                                                     searchString,
                                                                     startFrom,
                                                                     validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Retrieve the list of topic metadata elements with a matching qualified or display name.
     * There are no wildcards supported on this request.
     *
     * @param userId calling user
     * @param name name to search for
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of matching metadata elements
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<TopicElement>   getTopicsByName(String userId,
                                                String name,
                                                int    startFrom,
                                                int    pageSize) throws InvalidParameterException,
                                                                        UserNotAuthorizedException,
                                                                        PropertyServerException
    {
        final String methodName        = "getTopicsByName";
        final String nameParameterName = "name";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateName(name, nameParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/by-name/{2}?startFrom={3}&pageSize={4}";

        TopicsResponse restResult = restClient.callTopicsGetRESTCall(methodName,
                                                                     urlTemplate,
                                                                     serverName,
                                                                     userId,
                                                                     name,
                                                                     startFrom,
                                                                     validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Retrieve the list of topics created by this caller.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the topic manager (event broker)
     * @param eventBrokerName unique name of software server capability representing the topic manager (event broker)
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of matching metadata elements
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<TopicElement> getTopicsForEventBroker(String userId,
                                                      String eventBrokerGUID,
                                                      String eventBrokerName,
                                                      int    startFrom,
                                                      int    pageSize) throws InvalidParameterException,
                                                                                 UserNotAuthorizedException,
                                                                                 PropertyServerException
    {
        final String methodName = "getTopicsForEventBroker";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "?startFrom={4}&pageSize={5}";

        TopicsResponse restResult = restClient.callTopicsGetRESTCall(methodName,
                                                                     urlTemplate,
                                                                     serverName,
                                                                     userId,
                                                                     eventBrokerGUID,
                                                                     eventBrokerName,
                                                                     startFrom,
                                                                     validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Retrieve the topic metadata element with the supplied unique identifier.
     *
     * @param userId calling user
     * @param guid unique identifier of the requested metadata element
     *
     * @return matching metadata element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public TopicElement getTopicByGUID(String userId,
                                       String guid) throws InvalidParameterException,
                                                           UserNotAuthorizedException,
                                                           PropertyServerException
    {
        final String methodName = "getTopicByGUID";
        final String guidParameterName = "guid";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(guid, guidParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/{2}";

        TopicResponse restResult = restClient.callTopicGetRESTCall(methodName,
                                                                   urlTemplate,
                                                                   serverName,
                                                                   userId,
                                                                   guid);

        return restResult.getElement();
    }


    /* ============================================================================
     * A topic may host one or more event types depending on its capability
     */

    /**
     * Create a new metadata element to represent a event type.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param topicGUID unique identifier of the topic where the event type is located
     * @param properties properties about the event type
     *
     * @return unique identifier of the new event type
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public String createEventType(String              userId,
                                  String              eventBrokerGUID,
                                  String              eventBrokerName,
                                  String              topicGUID,
                                  EventTypeProperties properties) throws InvalidParameterException,
                                                                         UserNotAuthorizedException,
                                                                         PropertyServerException
    {
        final String methodName                     = "createEventType";
        final String parentElementGUIDParameterName = "topicGUID";
        final String propertiesParameterName        = "eventSchemaAttributeProperties";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(topicGUID, parentElementGUIDParameterName, methodName);
        invalidParameterHandler.validateObject(properties, propertiesParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/{4}/event-types";

        GUIDResponse restResult = restClient.callGUIDPostRESTCall(methodName,
                                                                  urlTemplate,
                                                                  properties,
                                                                  serverName,
                                                                  userId,
                                                                  eventBrokerGUID,
                                                                  eventBrokerName,
                                                                  topicGUID);

        return restResult.getGUID();
    }


    /**
     * Create a new metadata element to represent a event type using an existing metadata element as a template.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param templateGUID unique identifier of the metadata element to copy
     * @param topicGUID unique identifier of the topic where the event type is located
     * @param templateProperties properties that override the template
     *
     * @return unique identifier of the new event type
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public String createEventTypeFromTemplate(String             userId,
                                              String             eventBrokerGUID,
                                              String             eventBrokerName,
                                              String             templateGUID,
                                              String             topicGUID,
                                              TemplateProperties templateProperties) throws InvalidParameterException,
                                                                                            UserNotAuthorizedException,
                                                                                            PropertyServerException
    {
        final String methodName                     = "createEventTypeFromTemplate";
        final String templateGUIDParameterName      = "templateGUID";
        final String parentElementGUIDParameterName = "topicGUID";
        final String propertiesParameterName        = "templateProperties";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(templateGUID, templateGUIDParameterName, methodName);
        invalidParameterHandler.validateGUID(topicGUID, parentElementGUIDParameterName, methodName);
        invalidParameterHandler.validateObject(templateProperties, propertiesParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/{4}/event-types/from-template/{5}";

        GUIDResponse restResult = restClient.callGUIDPostRESTCall(methodName,
                                                                  urlTemplate,
                                                                  templateProperties,
                                                                  serverName,
                                                                  userId,
                                                                  eventBrokerGUID,
                                                                  eventBrokerName,
                                                                  topicGUID,
                                                                  templateGUID);

        return restResult.getGUID();
    }


    /**
     * Update the metadata element representing a event type.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param eventTypeGUID unique identifier of the metadata element to update
     * @param isMergeUpdate are unspecified properties unchanged (true) or removed?
     * @param properties new properties for the metadata element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void updateEventType(String              userId,
                                String              eventBrokerGUID,
                                String              eventBrokerName,
                                String              eventTypeGUID,
                                boolean             isMergeUpdate,
                                EventTypeProperties properties) throws InvalidParameterException,
                                                                       UserNotAuthorizedException,
                                                                       PropertyServerException
    {
        final String methodName               = "updateEventType";
        final String elementGUIDParameterName = "eventTypeGUID";
        final String propertiesParameterName  = "topicProperties";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(eventTypeGUID, elementGUIDParameterName, methodName);
        invalidParameterHandler.validateObject(properties, propertiesParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/event-types/{4}";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        properties,
                                        serverName,
                                        userId,
                                        eventBrokerGUID,
                                        eventBrokerName,
                                        eventTypeGUID);
    }


    /**
     * Remove the metadata element representing a event type.
     *
     * @param userId calling user
     * @param eventBrokerGUID unique identifier of software server capability representing the event broker
     * @param eventBrokerName unique name of software server capability representing the event broker
     * @param eventTypeGUID unique identifier of the metadata element to remove
     * @param qualifiedName unique name of the metadata element to remove
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public void removeEventType(String userId,
                                String eventBrokerGUID,
                                String eventBrokerName,
                                String eventTypeGUID,
                                String qualifiedName) throws InvalidParameterException,
                                                             UserNotAuthorizedException,
                                                             PropertyServerException
    {
        final String methodName                  = "removeEventType";
        final String elementGUIDParameterName    = "eventTypeGUID";
        final String qualifiedNameParameterName  = "qualifiedName";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventBrokerGUID, eventBrokerGUIDParameterName, methodName);
        invalidParameterHandler.validateName(eventBrokerName, eventBrokerNameParameterName, methodName);
        invalidParameterHandler.validateGUID(eventTypeGUID, elementGUIDParameterName, methodName);
        invalidParameterHandler.validateName(qualifiedName, qualifiedNameParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + editURLTemplatePrefix + "/event-types/{4}/{5}/delete";

        restClient.callVoidPostRESTCall(methodName,
                                        urlTemplate,
                                        nullRequestBody,
                                        serverName,
                                        userId,
                                        eventBrokerGUID,
                                        eventBrokerName,
                                        eventTypeGUID,
                                        qualifiedName);
    }


    /**
     * Retrieve the list of event type metadata elements that contain the search string.
     * The search string is treated as a regular expression.
     *
     * @param userId calling user
     * @param searchString string to find in the properties
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of matching metadata elements
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<EventTypeElement>   findEventTypes(String userId,
                                                   String searchString,
                                                   int    startFrom,
                                                   int    pageSize) throws InvalidParameterException,
                                                                           UserNotAuthorizedException,
                                                                           PropertyServerException
    {
        final String methodName                = "findEventTypes";
        final String searchStringParameterName = "searchString";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateSearchString(searchString, searchStringParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/event-types/by-search-string/{2}?startFrom={3}&pageSize={4}";

        EventTypesResponse restResult = restClient.callEventTypesGetRESTCall(methodName,
                                                                             urlTemplate,
                                                                             serverName,
                                                                             userId,
                                                                             searchString,
                                                                             startFrom,
                                                                             validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Return the list of event types associated with an EvenSet.  This is a collection of EventType definitions.
     * These event types can be used as a template for adding the event types to a topic.
     *
     * @param userId calling user
     * @param eventSetGUID unique identifier of the topic to query
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of metadata elements describing the event types associated with the requested EventSet
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<EventTypeElement> getEventTypesForEventSet(String userId,
                                                           String eventSetGUID,
                                                           int    startFrom,
                                                           int    pageSize) throws InvalidParameterException,
                                                                                   UserNotAuthorizedException,
                                                                                   PropertyServerException
    {
        final String methodName                     = "getEventTypesForTopic";
        final String parentElementGUIDParameterName = "topicGUID";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(eventSetGUID, parentElementGUIDParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + "/servers/{0}/open-metadata/access-services/data-manager/users/{1}/event-sets/{2}/event-types?startFrom={3}&pageSize={4}";

        EventTypesResponse restResult = restClient.callEventTypesGetRESTCall(methodName,
                                                                             urlTemplate,
                                                                             serverName,
                                                                             userId,
                                                                             eventSetGUID,
                                                                             startFrom,
                                                                             validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Return the list of event-types associated with a topic.
     *
     * @param userId calling user
     * @param topicGUID unique identifier of the topic to query
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of metadata elements describing the event-types associated with the requested topic
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<EventTypeElement> getEventTypesForTopic(String userId,
                                                        String topicGUID,
                                                        int    startFrom,
                                                        int    pageSize) throws InvalidParameterException,
                                                                                UserNotAuthorizedException,
                                                                                PropertyServerException
    {
        final String methodName                     = "getEventTypesForTopic";
        final String parentElementGUIDParameterName = "topicGUID";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(topicGUID, parentElementGUIDParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/{2}/event-types?startFrom={3}&pageSize={4}";

        EventTypesResponse restResult = restClient.callEventTypesGetRESTCall(methodName,
                                                                             urlTemplate,
                                                                             serverName,
                                                                             userId,
                                                                             topicGUID,
                                                                             startFrom,
                                                                             validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Retrieve the list of event type metadata elements with a matching qualified or display name.
     * There are no wildcards supported on this request.
     *
     * @param userId calling user
     * @param name name to search for
     * @param startFrom paging start point
     * @param pageSize maximum results that can be returned
     *
     * @return list of matching metadata elements
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public List<EventTypeElement>   getEventTypesByName(String userId,
                                                        String name,
                                                        int    startFrom,
                                                        int    pageSize) throws InvalidParameterException,
                                                                                UserNotAuthorizedException,
                                                                                PropertyServerException
    {
        final String methodName        = "getEventTypesByName";
        final String nameParameterName = "name";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateName(name, nameParameterName, methodName);
        int validatedPageSize = invalidParameterHandler.validatePaging(startFrom, pageSize, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/event-types/by-name/{2}?startFrom={3}&pageSize={4}";

        EventTypesResponse restResult = restClient.callEventTypesGetRESTCall(methodName,
                                                                             urlTemplate,
                                                                             serverName,
                                                                             userId,
                                                                             name,
                                                                             startFrom,
                                                                             validatedPageSize);

        return restResult.getElementList();
    }


    /**
     * Retrieve the event type metadata element with the supplied unique identifier.
     *
     * @param userId calling user
     * @param guid unique identifier of the requested metadata element
     *
     * @return requested metadata element
     *
     * @throws InvalidParameterException  one of the parameters is invalid
     * @throws UserNotAuthorizedException the user is not authorized to issue this request
     * @throws PropertyServerException    there is a problem reported in the open metadata server(s)
     */
    @Override
    public EventTypeElement getEventTypeByGUID(String userId,
                                               String guid) throws InvalidParameterException,
                                                                   UserNotAuthorizedException,
                                                                   PropertyServerException
    {
        final String methodName        = "getEventTypeByGUID";
        final String guidParameterName = "guid";

        invalidParameterHandler.validateUserId(userId, methodName);
        invalidParameterHandler.validateGUID(guid, guidParameterName, methodName);

        final String urlTemplate = serverPlatformURLRoot + retrieveURLTemplatePrefix + "/event-types/{2}";

        EventTypeResponse restResult = restClient.callEventTypeGetRESTCall(methodName,
                                                                           urlTemplate,
                                                                           serverName,
                                                                           userId,
                                                                           guid);

        return restResult.getElement();
    }
}
