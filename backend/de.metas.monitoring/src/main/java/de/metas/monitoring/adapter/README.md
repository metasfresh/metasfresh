## Micrometer Performance Monitoring

This is used to create [micrometer](https://micrometer.io/) [timer](https://micrometer.io/docs/concepts#_timers) metrics. The metrics are available at endpoint `/prometheus` in app and webapi.
Every metric contains at least 2 labels:

| Label    | Description                                                  |
|----------|--------------------------------------------------------------|
| `name`   | usually the classname of the monitored function              |
| `action` | usually the name of the monitored function                   |        

All metrics are grouped in meter by `Types` like `DB` and named `mf_<type>`.

For monitored HTTP-Requests and the underlying functions every function called by an HTTP-Request is set in relation to it via Labels (in same Thread only)

| Label        | Description                                                                        |
|--------------|------------------------------------------------------------------------------------|
| `depth`      | depth of monitored functions (starts at depth 1 for HTTP-Request)                  |
| `initiator`  | name of HTTP-Request a monitored function is related to (className + functionName) |
| `windowId`   | if exists (windowName + (windowId) )                                               |
| `callerName` | className + functionName of monitored function                                     |
| `calledBy`   | `callerName` of monitored "parent" function                                        |

This metrics can be collected and visualised by [Prometheus](https://prometheus.io/) (+ [Grafana](https://grafana.com/)) or any other Monitoring System.

The monitoring can be enabled in the System Configuration of metasfresh. Every `Type` except `DB`can be turned on separately and can be found under the name `de.metas.monitoring.*`.
For Type `DB` rest calls can be used:
- app `/api/v2/test/recordSqlQueriesWithMicrometer`
- webapi `/rest/api/debug/recordSqlQueriesWithMicrometer`

