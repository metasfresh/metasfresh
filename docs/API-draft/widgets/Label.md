# Widget

## `Label` type (WIP)

An informational label for state/status.

### `GET /api/layout`

```json
{
    "caption": "Label",
    "description": "Informational label, nothing to edit",
    "widgetType": "Label",
    "validation": [],
    "fields": [
        {
            "field": "DeliveryStatus"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "DeliveryStatus",
    "value": ?
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "DeliveryStatus",
    "value": ?
}
```
```json
// changed field response example
{
    "field": "DeliveryStatus",
    "value": ?
}
```
