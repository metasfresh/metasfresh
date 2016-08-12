# Widget

## `Integer` type

Non-decimal numeric input type.

### `GET /api/layout`

```json
{
    "caption": "Integer",
    "description": "Input for non-decimal numbers",
    "widgetType": "Integer",
    "validation": [],
    "fields": [
        {
            "field": "Pay_BPartner_ID"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "Pay_BPartner_ID ",
    "value": "1236",
    "readonly": false,
    "mandatory": true,
    "displayed": true
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "Pay_BPartner_ID",
    "value": "1238"
}
```
```json
// changed field response example
{
    "field": "Pay_BPartner_ID",
    "value": "1356"
}
```

## `Number` type

Same as `Integer`.

## `Amount` type

Same as `Number`, may apply additional parameters.

## `Quantity` type

Same as `Number`, may apply additional parameters.

## `CostPrice` type

Decimal field formatted due to locale settings.