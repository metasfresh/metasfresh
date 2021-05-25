

# DisputeSummaryResponse

This type defines the base response payload of the getPaymentDisputeSummaries method. Each payment dispute that matches the input criteria is returned under the paymentDisputeSummaries array.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**href** | **String** | The URI of the getPaymentDisputeSummaries call request that produced the current page of the result set. |  [optional]
**limit** | **Integer** | This value shows the maximum number of payment disputes that will appear on one page of the result set. The limit value can be passed in as a query parameter in the request, or if it is not used, it defaults to 200. If the value in the total field exceeds this limit value, there are multiple pages in the current result set. Min: 1; Max: 200; Default: 200 |  [optional]
**next** | **String** | The getPaymentDisputeSummaries call URI to use if you wish to view the next page of the result set. For example, the following URI returns records 11 thru 20 from the collection of payment disputes: path/payment_dispute_summary?limit&#x3D;10&amp;amp;offset&#x3D;10 This field is only returned if there is a next page of results to view based on the current input criteria. |  [optional]
**offset** | **Integer** | This integer value indicates the number of payment disputes skipped before listing the first payment dispute from the result set. The offset value can be passed in as a query parameter in the request, or if it is not used, it defaults to 0 and the first payment dispute of the result set is shown at the top of the response. |  [optional]
**paymentDisputeSummaries** | [**List&lt;PaymentDisputeSummary&gt;**](PaymentDisputeSummary.md) | Each payment dispute that matches the input criteria is returned under this array. If no payment disputes are found, an empty array is returned. |  [optional]
**prev** | **String** | The getPaymentDisputeSummaries call URI to use if you wish to view the previous page of the result set. For example, the following URI returns records 1 thru 10 from the collection of payment disputes: path/payment_dispute_summary?limit&#x3D;10&amp;amp;offset&#x3D;0 This field is only returned if there is a previous page of results to view based on the current input criteria. |  [optional]
**total** | **Integer** | This integer value is the total number of payment disputes that matched the input criteria. If the total number of entries exceeds the value that was set for limit in the request payload, you will have to make multiple API calls to see all pages of the results set. This field is returned even if it is 0. |  [optional]



