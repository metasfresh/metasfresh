# Widget

## `List` type

A dropdown selector input. Allows you to display choices for `key:value` pairs and select one.

### `GET /api/layout`

```json
{
    "caption": "Dropdown",
    "description": "Dropdown selector input",
    "widgetType": "List",
    "validation": [],
    "fields": [
        {
            "field": "Bill_BPartner_ID"
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

## To get choices

Each time `choices` array is empty (or field doesn't exist in response) you need to load it.

#### Choices from API

`GET /api/dropdown?field=Bill_BPartner_ID&...&id=1234`

```json
[
    { "1236": "Partners Inc." },
    { "1458": "Jazzy Innovations" },
    { "121": "Alphabet Inc." }
]
```

#### Choices from `GET /api/data` or `PATCH /api/commit` (WIP)

Choices may be also filled from above endpoints as a result of initial entity load or changes made by user.

```json
// Examplary /api/data call
{
    "field": "Bill_BPartner_ID ",
    "value": { "1236": "Partners Inc." },
    "choices": [ { "1236": "Partners Inc." }, { "1458": "Jazzy Innovations" }, { "121": "Alphabet Inc." } ],
    "readonly": false,
    "mandatory": true,
    "displayed": true
}
```