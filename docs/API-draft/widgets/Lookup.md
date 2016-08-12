# Widget

## `Lookup` type

Complex lookup widget that supports typeahead suggestions and multi-step selection.
This widget may set more than one field.
This widget is mostly useful for master data.

### `GET /api/layout`

```json
{
    "caption": "Lookup",
    "description": "Search lookup widget with typeahead",
    "widgetType": "Lookup",
    "validation": [],
    "fields": [
        {
            "field": "C_BPartner_ID"
        },
        {
            "field": "C_BPartner_Location_ID"
        },
        {
            "field": "AD_User_ID"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "Bill_BPartner_ID ",
    "value": { "1236": "Partners Inc." },
    "choices": [],
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
    "path": "Bill_BPartner_ID",
    "value": { "1458": "Jazzy Innovations" }
}
```
```json
// changed field response example
{
    "field": "Bill_BPartner_ID",
    "value": { "1458": "Jazzy Innovations" }
}
```

## To get suggestions

#### Choices from API

`GET /api/typeahead?field=C_BPartner_ID&query=a&...&id=1234`

> **field** – field name
> **query** – user input for suggestion

```json
[
    { "1236": "Partners Inc." },
    { "1458": "Jazzy Innovations" },
    { "121": "Alphabet Inc." }
]
```
