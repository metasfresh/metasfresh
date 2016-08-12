# Sales order 1023

### User displays sales order 1023:

`GET /sales-order/1023`
`Cookie: SESSION_ID abcd1234` *(so we know which user)*

### Web UI – backend API communication:

#### 1. Get layout

`GET /api/windows?window=sales-order`
`Cookie: SESSION_ID abcd1234`

Depending on user rights – a JSON with Sales order layout is a response:
```
{
// ...
  "middle": {
    "left": [
      {
	    "widget": "multi_lookup",
	    "fields": [
	      { "name": "business_partner_id" },
	      { "name": "business_partner_location_id" },
	      { "name": "business_partner_person_id" },
	    ]
	  },
	  // ...
    ],
    "right": [
      {
        "widget": "datetime",
        "field": "order_date"
      },
      {
        "widget": "datetime",
        "field": "promised_date"
      },
      // ...
    ]
  }
}
```

**Based on this we can build a proper static layout with particular widgets to be used.**

#### 2. Get fields data

`GET /api/fields?window=sales-order`
`Header: Accept-Language pl, en-us;q=0.9, de;q=0.4`

A list of all fields and their properties is a response:
```
[
  {
    "business_partner_id": {
	  "caption": "Business partner",
	  "required": true,
	  "showOnDetails": true,
	  "showOnList": true,
	  "data": {
	    "type": "typeahead",
	    "uri": "/api/partners?query={query}&doctype={doctype}&docid={docid}",
	    "displayValue": "name",
	    "paramValue": "id",
	    "regEx": "\d"
	  }
    },
    "business_partner_location_id": {
	  "caption": "Business partner location",
	  "required": true,
	  "showOnDetails": true,
	  "showOnList": false,
	  "data": {
	    "type": "typeahead",
	    "uri": "/api/locations?partnerid={partnerid}",
	    "displayValue": "address",
	    "paramValue": "id",
	    "regEx": "\d"
	  }
    },
    "business_partner_person_id": {
	  "caption": "Business partner person",
	  "required": true,
	  "showOnDetails": true,
	  "showOnList": false,
	  "data": {
	    "type": "typeahead",
	    "uri": "/api/people?locationid={locationid}",
	    "displayValue": "fullname",
	    "paramValue": "id",
	    "regEx": "\d"
	  }
    },
    "order_date": {
      "caption": "Order date",
	  "required": true,
	  "showOnDetails": true,
	  "showOnList": true,
	  "data": {
	    "type": "datetime",
	    "regEx": "(\d{4})-(\d{2})-(\d{2})T(\d{2})\:(\d{2})\:(\d{2})"
	  }
    },
    "promised_date": {
      "caption": "Promised date",
	  "required": false,
	  "showOnDetails": false,
	  "showOnList": false,
	  "data": {
	    "type": "datetime",
	    "regEx": "(\d{4})-(\d{2})-(\d{2})T(\d{2})\:(\d{2})\:(\d{2})"
	  }
    },
    // ...
  }
]
```

**Based on this we attach background data to given layout. What should be data format, where can we get suggestions, what is the caption or placeholder etc.**

#### 3. Fill data for particular sales order

`GET /api/sales-order?id=1023`
`Cookie: SESSION_ID abcd1234`

A model with sales order object is a response:
```
{
  "id": 1023,
  "business_partner_id": 45,
  "business_partner_location_id": 865,
  "business_partner_person_id": 235,
  "order_date": "2014-06-03T13:13:45",
  "promised_date": null,
  // ...
}
```

**With this we fill data for already existing document. This call is not performed when creating new document.**

#### 3. User is changing business partner

User cleared current partner and entered "jaz".

`GET /api/partners?query=jaz&doctype=sales-order&docid=1023`
`Cookie: SESSION_ID abcd1234`

Suggestions was returned as response:

```
[
  {
    "id": 56,
    "name": "Jazzy Innovations SA"
  },
  {
    "id": 31,
    "name": "We Got Some Jazz ltd."
  },
  {
    "id": 124,
    "name": "Jazz Players inc."
  }
]
```

**When user cleared business partner field – all depending fields were cleared too (it is defined by layout hierarchy).**

#### 4. User changed business partner and now is time to select its location

User selected "Jazz Players inc." (id=124). We get locations for it.

`GET /api/locations?partnerid=124`
`Cookie: SESSION_ID abcd1234`

Locations for perticular partner were responsed:

```
[
  {
    "id": 923,
    "address": "Some Address 245, 3016E Location"
  }
]
```

**As only one record was responded, we don't display choice selector to user. We set is as selected (id=923).**

#### 5. Based on partner location location we select business contact person

I assumed "person" depends on business partner location (cause IDK).

`GET /api/people?locationid=923`
`Cookie: SESSION_ID abcd1234`

And it responded with:

```
[
  {
    "id": 667,
    "fullName": "Seweryn Zeman"
  },
  {
    "id": 668,
    "fullName": "Mark Krake"
  }
]
```

**User selected Mark here (id=668).**

**We have two choices here so we display selector to the user.**

#### 6. It is time now to save User changes

```
PATCH /api/sales-order?id=1023
Cookie: SESSION_ID abcd1234

[
    { "op": "replace", "path": "business_partner_id", "value": 124 },
    { "op": "replace", "path": "business_partner_location_id", "value": 923 },
    { "op": "replace", "path": "business_partner_person_id", "value": 668 }
]
```

**This way we changed a set of data and updated database with new values.**
