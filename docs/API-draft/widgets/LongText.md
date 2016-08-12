# Widget

## `LongText` type

Used for long, multi-line text inputs.

### `GET /api/layout`

```json
{
    "caption": "Long text",
    "description": "Input for long texts",
    "widgetType": "LongText",
    "validation": [],
    "fields": [
        {
            "field": "Description"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "Description",
    "value": "Some long text that may take a lot, lot lines of text...",
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
    "path": "Description",
    "value": "New long text that should replace previous one..."
}
```
```json
// changed field response example
{
    "field": "Description",
    "value": "Deutsch",
    "readonly": true
}
```