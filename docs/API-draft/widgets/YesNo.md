# Widget

## `YesNo` type

Checkbox widget.

> Data format is `true`/`false` as a value.

### `GET /api/layout`

```json
{
    "caption": "Checkbox",
    "description": "To select or not to select",
    "widgetType": "YesNo",
    "validation": [],
    "fields": [
        {
            "field": "IsDropShip"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "IsDropShip",
    "value": true
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "IsDropShip",
    "value": false
}
```
```json
// changed field response example
{
    "field": "IsDropShip",
    "value": false
}
```

## `Switch` type

Same as `YesNo`, only difference is different widget visual look.