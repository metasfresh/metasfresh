

# OrderSearchPagedCollection

This type contains the specifications for the collection of orders that match the search or filter criteria of a getOrders call. The collection is grouped into a result set, and based on the query parameters that are set (including the limit and offset parameters), the result set may included multiple pages, but only one page of the result set can be viewed at a time.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**href** | **String** | The URI of the getOrders call request that produced the current page of the result set. |  [optional]
**limit** | **Integer** | The maximum number of orders returned per page of the result set. The limit value can be passed in as a query parameter, or if omitted, its value defaults to 50. Note: If this is the last or only page of the result set, the page may contain fewer orders than the limit value. To determine the number of pages in a result set, divide the total value (total number of orders matching input criteria) by this limit value, and then round up to the next integer. For example, if the total value was 120 (120 total orders) and the limit value was 50 (show 50 orders per page), the total number of pages in the result set is three, so the seller would have to make three separate getOrders calls to view all orders matching the input criteria. Default: 50 |  [optional]
**next** | **String** | The getOrders call URI to use if you wish to view the next page of the result set. For example, the following URI returns records 41 thru 50 from the collection of orders: path/order?limit&#x3D;10&amp;amp;offset&#x3D;40 This field is only returned if there is a next page of results to view based on the current input criteria. |  [optional]
**offset** | **Integer** | The number of results skipped in the result set before listing the first returned result. This value can be set in the request with the offset query parameter. Note: The items in a paginated result set use a zero-based list where the first item in the list has an offset of 0. |  [optional]
**orders** | [**List&lt;Order&gt;**](Order.md) | This array contains one or more orders that are part of the current result set, that is controlled by the input criteria. The details of each order include information about the buyer, order history, shipping fulfillments, line items, costs, payments, and order fulfillment status. By default, orders are returned according to creation date (oldest to newest), but the order will vary according to any filter that is set in request. |  [optional]
**prev** | **String** | The getOrders call URI for the previous result set. For example, the following URI returns orders 21 thru 30 from the collection of orders: path/order?limit&#x3D;10&amp;amp;offset&#x3D;20 This field is only returned if there is a previous page of results to view based on the current input criteria. |  [optional]
**total** | **Integer** | The total number of orders in the results set based on the current input criteria. Note: If no orders are found, this field is returned with a value of 0. |  [optional]
**warnings** | [**List&lt;Error&gt;**](Error.md) | This array is returned if one or more errors or warnings occur with the call request. |  [optional]



