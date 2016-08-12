# Widget

## `DateTime` type

Date picker input where you can select date and corresponding time.

> Default format for date and time is ISO8601 with timezone.
> F.e. `2016-11-06T08:15:30+02:00`

### `GET /api/layout`

```json
{
    "caption": "Date and time picker",
    "description": "Click to pick a date and time",
    "widgetType": "DateTime",
    "validation": [],
    "fields": [
        {
            "field": "DatePromised"
        }
    ]
}
```

### `GET /api/data?...&id=1234`

```json
{
    "field": "DatePromised",
    "value": "2016-11-05T08:15:30+02:00"
}
```

### `PATCH /api/commit?...&id=1234`

```json
// request body example
{
    "op": "replace",
    "path": "DatePromised",
    "value": "2016-11-06T09:15:30+02:00"
}
```
```json
// changed field response example
{
    "field": "DatePromised",
    "value": "2016-11-07T09:15:30+02:00"
}
```

## Validation (WIP)

In `validation` array you can specify this constraints:

#### `min`, `max`

Earliest and latest date to be selected. Only one can be specified.

```json
{
    "validation": [
        "min": "2010-01-01T00:00:01+02:00",
        "max": "2040-12-31T23:59:59+02:00"
    ]
}
```