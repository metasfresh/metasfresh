# Widget

## `Text` type

Used for relatively short text inputs.

### `GET /api/layout`

```json
{
    "caption": "Short text",
    "description": "Input for relatively short texts",
    "widgetType": "Text",
    "validation": [],
    "fields": [
        {
            "field": "AD_Language"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "AD_Language",
    "value": "English",
    "readonly": false,
    "mandatory": false,
    "displayed": true
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "AD_Language",
    "value": "Deutsche"
}
```
```json
// changed field response example
{
    "field": "AD_Language",
    "value": "Deutsch",
    "readonly": true
}
```