**MASS JSON Requests to metasfresh**
---

* There is a HTTP rest endpoint (`/camel/metasfresh`) that can be secured using `ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey`.
* metasfresh can send feedback about the processed data to an external resource URL that can be specified using  `ExternalSystem_Config_Metasfresh.FeedbackResourceURL`.
  The authentication token used for connecting to the configured feedback resource is specified using `ExternalSystem_Config_Metasfresh.FeedbackResourceAuthToken`
    * one can find those settings within `External system config` window (i.e. `windowId` = `541024`)

* Currently, the `metasfresh` rest route can handle 1 entity type: (routing by `request.itemType`)
    * `itemType` = `bpartnerComposite` translates to `processBPartnerFromFile` route;

**JsonMassUpsertRequest => metasfresh C_BPartner**
---

* `JsonMassUpsertRequest` - payload sent to the camel MassJsonrest endpoint
    * see: `de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertRequest`

* `JsonMassUpsertFeedbackRequest` - payload sent to the configured feedback resource
    * see: `de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertFeedbackRequest`

JsonMassUpsertRequest | metasfresh-json            | JsonMassUpsertFeedbackRequest | note                                                                                                                   |
---- |----------------------------|-------------------------------|------------------------------------------------------------------------------------------------------------------------|
batchId| ----                       | batchId                       | sent back on the JsonMassUpsertFeedbackRequest                                                                         
itemType| ----                       | ----                          | used for determining the type of the JsonMassUpsertRequest, must be `bpartnerComposite` for `C_BPartner`               |
itemBody| ----                       | ----                          | contains the request items that are going to be upserted                                                               |
itemBody.requestItems| JsonRequestBPartnerUpsert.requestItems | ----                          | ----                                                                                                                   |
----|----| finishDate                    | Date and time at which the feedback is sent                                                                            |
----|----| itemCount                    | The number of JsonRequestBPartnerUpsert.requestItems successfully upserted in metasfresh                               |
----|----| success                    | True if all JsonMassUpsertRequest.itemBody.requestItems has been successfully upserted in metasfresh                   |
----|----| errorInfo                    | The cause why one or many JsonMassUpsertRequest.itemBody.requestItems has not been successfully upserted in metasfresh |