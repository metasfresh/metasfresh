
## material-dispo data flow and dependencies

The components are:

* event
  * contains event-pojos
  * events are currently send via the metasfresh distributed event framework (using JMS)
  
* commons
  * depends on event
  
* service(standalone)
  * gets events from model interceptors (swing, app etc), not communicating back with swing, app etc; it's one way communication
  * gets recomandations events from PLANNING about the demands events which were send earlier
  * sends a demand event to PLANNING, gets back a recomandation and 
  * depsends on: commons and event
  
* planning:
  * gets an demand event from SERVICE, sends back a recomendation
  * depends on event, libero


* client
  * offers a richer interface (e.g. with UOM) to callers. Supposed to be *external* to the standalone service.
  * depends on commons

## Candidate records

Note: this is taking place in metasfresh-material-dispo-service

The material disposition framework's data mostly consists of *candidate* records (table name `MD_Candidate`). 
They are created by the system in an *incremental* fashion, accordingly to the system's master data (BOMs, Distribution networks etc) and can be inspected and changed by users.


Candidates have different types:
* `stock`
* `demand`
* `supply`
* `stock-up`
* `unrelated descrease`
* `unrelated increase`

In addition to its type, the most notable properties of a candidate are
* date: the (future) time to which the given record relates
* warehouse
* product
* quantity


Candidates are linked with each other via
* parentId: 
  * used to link a supply with its parent stock candidate
  * also used to link a stock with its parent demand candidate
* groupId: used to link supply and demand records with each other. We need this for production orders. There we have e.g. a supply candidate for a "salad" product and multiple demand records for the different ingredients that are needed to supply the salad 
	
	
### `stock` candidate	

Used to manage the projected quantity at 'timestamp'.

A stock candidate can be the *child* of a demand candidate or the *parent* of a supply.

The projected qty of a 'stock' record is increased by it's child 'supply' candidates and decreased by it's parent 'demand' candidates

A `stock` candidate can have a negative qty if the MRP system does not know how to balance the respective demand. That information (negative qty/"unbalanced demand") can be displayed in the MRP product info window used by the purchase department, so they know what to order for which date.

When a `stock` record for given product, warehouse and timestamp is changed, this change is propagated to other `stock` records that have the same product and locator but later timestamps.
 
 
### `demand` candidate

A demand record can directly relate e.g. to a shipment schedule, or to the "source" part of a distribution order, or to the "issue" part of a production order.

It can be associated with a supply candidate via its groupid.
It *decreases* the quantity of its child's `stock` record. That's because the demand candidate is about compensating for a planned removal of goods.

* note: if the projected quantity sinks below zero, the system will trigger a "material demand event" that should lead to a new supply candidate (e.g. related to a planned production order) to balance the projected stock.


Also see the explantion about `stock-up` candidate.

### `supply` candidate

A supply record can directly relate e.g. to a receipt schedule or to the "destination" part of a distribution order or to the "receive" part of a production order.

It *increases* the quantity of its parent `stock` record

### `stock-up` candidate

A stock-up candidate can relate to a forecast document line and is somewhat similar to a demand candidate.
The similariy is that the system will fire a "material demand event" if the stock-up quantity doesn't match the respective projected quantity.

However, the stock-up quantity is not subtracted from the projected quantity.

### `unrelated descrease` and `unrelated increase` candidate

These types are used if the material service is notified about transaction (i.e. `M_Transaction`s) which it did not initiate and "didn't not know about".

## Sample workflow


* the user created and completed C_OrderLine with
 * 23kg Salad
 * Datepromised 2017-03-22

=> the system creates a shipment schedule (no material dispo until here)

=>the system creates a candidate "1_demand" with
* link to the shipment schedule
* type=`demand`
* quantity = 23kg 
* product = Salad
* warehouse from where the shipmentschedule shall be shipped
* timestamp=the shipmentschedule's DatePromised

=>the system creates a candidate "2_stock" which is a child of candidate "1" with 
* type=`stock`
* quantity = -23kg 
* product = Salad
* locator and timestamp from its parent

At this point in the (hypothetical) workflow the system does not know what else to do, because there is no distribution-network, BOM etc configured.
Therefore the negative projected quantity remains unbalanced. However, the information is shown.
 
Now someone creates a purchase order with: 
* 100kg Salad
* datepromised 2017-03-21 (i.e. one day before the sale order)
* warehouse which in this example is also the warehouse of the shipment schedule

=> the system creates a receipt schedule accordingly (no material dispo here)
 
=> The system then creates a candidate "3_supply" with
* link to the receipt schedule
* type=`supply`
* quantity, product and warehouse from the receipt schedule
* timestamp=the receipt schedule's DatePromised

=> the system then creates a candidate "4_stock" which is the *parent* of "3_supply", with
* link to the receipt schedule
* type=`stock`
* quantity = 100kg 
* product = Salad

=> the system then updates all candidates with type `stock` that have the same locator and product and have a later timestamp (namely "2_stock") and adds +100 to their quantity.
* that way, the quantity of "2_stock" is now changed from -23 to +77 and the demand was balanced
