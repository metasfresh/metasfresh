**MASS JSON Requests to metasfresh**
---

* There is a HTTP rest endpoint (`/camel/metasfresh`)
    * an authorization token must be set up via `ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey`
    * and sent in all requests via `X-Auth-Token` HTTP header
* metasfresh can send feedback about the processed data to an external resource if one is configured via `ExternalSystem_Config_Metasfresh.FeedbackResourceURL`
    * authentication for the feedback resource must be added in `ExternalSystem_Config_Metasfresh.FeedbackResourceAuthToken`
* all settings can be found within `External system config` window (i.e. `windowId` = `541024` - metasfresh tab)

---

* the rest endpoint consumes only JSON messages of type:

```JSON
{
  "batchId": "string",
  "itemType": "string",
  "itemBody": {}
}
```

* note: the JSON properties must be sent exactly in the order described above.

JsonMassUpsertRequest | note                                                                   |
----|------------------------------------------------------------------------|
batchId| sent back on the feedback object, if feedback settings are configured
itemType| used for determining the type of the itemBody                          |
itemBody| contains the actual request that is going to be processed              |

# Sending feedback

* as mentioned above, if feedback settings are configured, the process will collect and send the following metrics as JSON:

```JSON
{
  "batchId": "string",
  "finishDate": "instant",
  "itemCount": "integer",
  "success": "boolean",
  "errorInfo": "string"
}
```

JsonMassUpsertFeedbackRequest | note                                               |
 ---- |----------------------------------------------------|
batchId | JsonMassUpsertRequest.batchId                      |
finishDate                    | When the request was fully processed               |
itemCount | The number of `items` successfully processed       |
success | True if all `items` are successfully processed     |
errorInfo | All errors collected during the request processing |

---

* Currently, the `metasfresh` rest route can handle 1 item type:
    * `itemType` = `bpartnerComposite`
        * with `itemBody` being an object with a single property: `requestItems`
        * where `requestItems` is a `List` of standard metasfresh REST API `v2` `JsonRequestBPartnerUpsertItem` 
