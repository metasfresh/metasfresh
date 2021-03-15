# Phone

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**countryCode** | **String** | The seller&#x27;s country calling code. This field is needed if the buyer is located in a different country than the seller. It is also OK to provide if the buyer and seller are both located in the same country. For a full list of calling codes for all countries, see the countrycode.org site. |  [optional]
**number** | **String** | The seller&#x27;s primary phone number associated with the return address. When this number is provided in a contestPaymentDispute or contestPaymentDispute method, it is provided as one continuous numeric string, including the area code. So, if the phone number&#x27;s area code was &#x27;408&#x27;, a number in this field may look something like this: &amp;quot;number&amp;quot; : &amp;quot;4088084356&amp;quot; If the buyer is located in a different country than the seller, the seller&#x27;s country calling code will need to be specified in the countryCode field. |  [optional]
