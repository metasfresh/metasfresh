# Widget

## `Date` type

Date picker input where you can select only the date.

> Default format for date is ISO8601.
> F.e. `2016-11-06`

### `GET /api/layout`

```json
{
    "caption": "Date picker",
    "description": "Click to pick a date",
    "widgetType": "Date",
    "validation": [],
    "fields": [
        {
            "field": "DateOrdered"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "DateOrdered",
    "value": "2016-11-05"
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "DateOrdered",
    "value": "2016-11-06"
}
```
```json
// changed field response example
{
    "field": "DateOrdered",
    "value": "2016-11-07"
}
```

## Validation (WIP)

In `validation` array you can specify this constraints:

#### `min`, `max`

Earliest and latest date to be selected. Only one can be specified.

```json
{
    "validation": [
        "min": "2010-01-01",
        "max": "2040-12-31"
    ]
}
```