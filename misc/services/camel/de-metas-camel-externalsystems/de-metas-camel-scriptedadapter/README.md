This module provides a framework where one can provide simple JavaScript files 
to convert incoming messages into request-bodies for the metasfresh-API - actually the `to_mf` routes - and vice versa.

These JavaScript files need to adhere to certain convetions.
See the two templates:
- to process payloads outgoing from metasfresh, see `javascript_templates/from_metasfresh_template.js`
- to process payloads incoming from metasfresh, see `javascript_templates/to_metasfresh_template.js`

The route is invoked through `ExternalSystemRequest`s with
- `externalSystemName="ScriptedAdapter"`
- `command="ConvertMsgFromMF"`
- `parameters`:
    - `messageFromMetasfresh` (`ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT`): string that is passed as sole parameter to the javascript-function
    - `scriptIdentifier` (`ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER`): filename (without `.js`) of the JavaScript to invoke.
    - `outboundHttpEP` (`ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP`): http Endpoint to which the script's return value is forwarded
    - `outboundHttpToken` (`ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN`): set as value for the `Authorization` http-Header, when the Endpoint is invoked
    - `outboundHttpMethod` (`ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD`): http-method like `PUT` or `POST` for the http-Endpoint
