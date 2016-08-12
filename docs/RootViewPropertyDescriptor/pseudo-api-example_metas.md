# Sales order 1023

### User displays sales order 1023:

`GET /sales-order/1023`
`Cookie: SESSION_ID abcd1234` *(so we know which user)*

### Web UI – backend API communication:

#### 1. Get layout

*Notes:*

* I added the `prepopulated` property; For example, there might be a default value for the field `deliveryViaRule` ("shipped" or "picked up") that is determined by the the currently logged-in user.<br>
So either we'd need to always make this API call, or the data coming with Step 1. might advise the UI whether step 3. is required or not. ***OK***
* also note in general, the the property names will e.g. be C_BPartner_ID, C_BPartner_Location_ID and AD_User_ID.<br>
But of those names will be consistent everywhere, so i think you don't need to bother wheter the name is "AD_User_ID" or "business_partner_person_id" or just "123".<br>
So, in the reminder I leave the names you chose as they are. ***I was using just pseudo names for better readibility.***
* all of this here will also apply for master data.

`GET /api/windows?window=sales-order`
`Cookie: SESSION_ID abcd1234`

Depending on user rights – a JSON with Sales order layout is a response:
```
{
 "document": {
  "type": "basiclayout",
  "name": "sales-order"
  "prepopulated": true, // if true, the webui shall also perform step 3. "Fill data for particular sales order", even for new records

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
	  {
        "widget": "lookup",
        "field": "pricingSystem_id"
      },
      // ...
    ]
  }
}
```

Based on this we can build a proper static layout with particular widgets to be used.

#### 2. Get fields data

**Notes:**

* I'm removing the `URI` property. See further below for details. ***But we need ANY data about where where we can get data. If we can associate widget type (like lookup) with generic API endpoint (like /api/typeahead) then we also need a property name (like 'partner') to build full API endpoint (like /api/typeahead?partner). And it's not considered in your example.***
* I'm adding a `readonly` property, because some fields may be read-only. ***OK***
* I'm adding a get for the case of a specific sales order, because the field data is different for different sales orders.<br>
Example: if a sales order is already completed, then *most* fields are read-only. Otherwise, *most* fields are read-write.

**Question**

* Only after this data was received, the UI knows which parts of the basic layout (step 1.) will actually be shown, right?<br>
Will there by empty spaces for not-shown fields or can the UI just "collapse" around them? ***Empty spaces cannot be displayed, they will collapsed to nearest defined element in the same container (f.e. main.left).***


`GET /api/fields?window=sales-order?sales-order?id=1023`
***You cannot use multiple '?'. How is it above? Please fix.***
***Should it be /api/fields?window=sales-order&doctype=sales-order&docid=1023 ?***
`Header: Accept-Language pl, en-us;q=0.9, de;q=0.4`

A list of all fields and their properties is a response:
```
	"type": "field",
	"sales-order":
	[
	  {
		"business_partner_id": {
		  "caption": "Business partner",
		  "required": true,
		  "showOnDetails": true,
		  "showOnList": true,
		  "readonly": false,
		  "data": {
			"type": "typeahead",
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
		  "readonly": false,
		  "data": {
			"type": "typeahead",
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
		  "readonly": true,
		  "data": {
			"type": "typeahead",
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
		  "readonly": true,
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
		  "readonly": false,
		  "data": {
			"type": "datetime",
			"regEx": "(\d{4})-(\d{2})-(\d{2})T(\d{2})\:(\d{2})\:(\d{2})"
		  }
		},
		"pricingSystem_id": {
		  "caption": "Pricing System",
		  "required": true,
		  "showOnDetails": true,
		  "showOnList": false,
		  "readonly": false,
		  "data": {
			"type": "typeahead",
			"displayValue": "name",
			"paramValue": "id",
			"regEx": "\d"
		  }
		}
		// ...
	  }
	],
	"sales-order-line":
	[
		// ...
	]
```

Based on this we attach background data to given layout. What should be data format, where can we get suggestions, what is the caption or placeholder etc.

#### 3. Fill data for particular sales order

`GET /api/sales-order?id=1023`
`Cookie: SESSION_ID abcd1234`

A model with sales order object is a response:
```
	"type": "data",
	"sales-order":
	[
		{
		  "id": 1023,
		  "business_partner_id": 45,
		  "business_partner_location_id": 865,
		  "business_partner_person_id": 235,
		  "order_date": "2014-06-03T13:13:45",
		  "promised_date": null,
		  // ...
		},
		// ...
	],
	"sales-order-line":
	[
		{
			 // ...
		},
		// ..
	]
```

With this we fill data for already existing document.

**Note:**
* we might need to perform step 3 also for new documents, as i outlined in step 1. "Get layout"
***Then we can make this call required. If nothing will be responded – there will be nothing to do , but API will be consistent and unified.***

#### 3. User is changing business partner

User cleared current partner and entered "jaz".

`GET /api/typeahead?partner?query=jaz&doctype=sales-order&docid=1023`
***Again multiple ?. It's not compatible with HTTP RFC.***
***Did you mean /api/typeahead?type=partner&query=jaz&doctype=sales-order&docid=1023 ?***
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

***So this endpoint is used ONLY for suggestions in typeahead field?***

~~When user cleared business partner field – all depending fields were cleared too (it is defined by layout hierarchy).~~

**Notes:**

* Problem 1: just using the layout hierarchy is not sufficient. In general, arbitrary field changes might cause other fields to become stale.
For example, if you change the `business_partner_id`, also the `invoice_partner_id` will become stale. Ok, here we have both of them in "main/left", but for another example,
if `business_partner_location_id` is changed, then it might mean that `invoice_partner_id` remains valid, but `invoice_partner_location_id` becomes stale
* i think we should have a two service-URLs:
  * 1. a dedicated "typeahead" service
  * 2. see below


#### 4. User changed business partner and now is time to select its location

User selected "Jazz Players inc." (id=124). We get locations for it.

`GET /api/input?partners?id=124&doctype=sales-order&docid=1023`
***This is very nasty way of defining API endpoints.***
***Did you mean /api/input?type=partners&id=124&doctype=sales-order&docid=1023 (still nasty) ?***
***Since we're using GET method we should look one step further so:***
***GET /api/input?type=locations&partnerid=124&...***
`Cookie: SESSION_ID abcd1234`

The response is a partial sales order object:

```
[
	{
		"type": "field",
		[
			"promised_date": 
			{
				// this partner's trucks drive by a fixed schedule, so we know already when he will knock on our door. 
				// Assume that the date is not negotiable. 
				// If we can't prepare the order for this date, then there can be no order from this customer.
				"readonly": true, 
			},
		]
	},
	{
		"type": "data",
		"sales-order":
		{
			"business_partner_location_id":
			[
			  {
				"id": 923,
				"address": "Some Address 245, 3016E Location"
			  }
			],
			 // in this case, the API can not return any person without knowing the location. 
			 // For another partner, the API might e.g. have returned two possible persons, even without knowing the location.
			"business_partner_person_id": null
			
			// this partner never receives shipments; they will pick up whatever they order from our warehouse.
			"deliveryRule": [ "pickup" ],
			
			// partner 124 has only one possible pricing system.
			"pricingSystem_id": 
			[
				{
					"id": 876,
					"name": "Special retailer pickup prices"
				}
			],
			
			// this is the date according to our schedule (i'm not making it up, this info can be put into our masterdata :-) ).
			"promised_date": "2014-06-03T13:13:45" 
		}
		
		// since the API figured out that the pricing system changed, it also recalculated the prices and returns the result
		"sales-order-lines": 
		[
			{
				"id": "123",
				"priceActual": 32.1
			}
			{
				"id": "124",
				"priceActual": 20.99
			}
		]
	}
]
```

***And endpoint here is already used to save changes to particular field? If so – it's extremely wrong.***

***The format for response sounds resonable though.***

As only one business_partner_location_id was responded, we don't display choice selector to user. We set is as selected (id=923).

**Note:**
* So, we notify the API about the change and the API sorts out which are the consequences for other fields.



#### 5. Based on partner location location we select business contact person

`GET /api/input?locations?id=923&doctype=sales-order&docid=1023`
***Again, we already GET locations, so now – person:***
***GET /api/input?type=person&location_id=923&...***
`Cookie: SESSION_ID abcd1234`

And it responds with:

```
[
	{
		"type": "data",
		"sales-order":
		{
			"business_partner_person_id": 
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
		}
	}
]
```

* User selected Mark here (id=668).
* We have two choices here so we display selector to the user.

**Note:**

* We assume that the API figured out that the new location is in the same country as the old one. Therefore, only `business_partner_person_id` is affected.
* In the case of a country change, the API might e.g. have send back new VAT rates for each order line.

#### 6. It is time now to *commit* User changes

```
PATCH /api/commit?sales-order?id=1023
Cookie: SESSION_ID abcd1234
```

This way we changed a set of data and updated database with new values.

**Note:**

* in this scenario, the API actually knows already about all the individual changes. That's why i wrote *commit*. I think we don't actually need to send the data again.

***Anything but POST, PATCH and PUT should ever update anything in database! As far, we called only GETs, so we broken HTTP RFC!***

