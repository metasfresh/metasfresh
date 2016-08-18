# Typeahead suggestions API

`GET /api/typeahead?field=C_BPartner_ID&query=jaz&type=143&id=7765`

> **field** – field name for which we shall suggest
> **query** – user input string
> **type** – document type identifier
> **id** – document entity ID

```json
[
    { "12567": "Jazzy Innovations" },
    { "18363": "Music is Jazz" },
    { "14733": "Arjazzyra" },
    { "19573": "JAZEC Inc." }
]
```