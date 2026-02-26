# Docu aboud the DESADV-JSON - WIP

- `Packing` is roughly speaking the LU.
- `Packing/Line_Item` is/are roughly speaking the TU(s)
- `Packing/Line_Item/QtyTU`:
    - Number of Trade-Units. Greater than 1 if there are multiple homogeneous TUs for one order position. Can also be 1 and there can be multiple items for inhomogeneous TUs (e.g. Catch-Weight)

- `Packing/Line_Item/DesadvLine/QtyOrderedInDesadvLineUOM`:
    - quantity ordered by the customer in the unit of measure `Packing/Line_Item/DesadvLine/DesadvLineUOM/X12DE355`
    - can also be "TU" or "COLI". In that case the customer typically ordered in cartons, and then the number is the quantity of ordered TUs

- Attention, `Packing/Line_Item/DesadvLine` refers to the respective order position. This means the same DesadvLine could be listed in more than one Item. All then have the same value for `Packing/Line_Item/DesadvLine/DesadvLine`
- `Packing/Line_Item/DesadvLine/QtyDeliveredInDesadvLineUOM`:
    - Like `QtyOrderedInDesadvLineUOM`, but for the delivered quantity

- `Packing/Line_Item/QtyCUsPerLU`: Number of CUs in the respective LU, in the unit of measure `Packing/Line_Item/DesadvLine/DesadvLineUOM/X12DE355` (like QtyOrderedInDesadvLineUOM & QtyDeliveredInDesadvLineUOM)
- `Packing/Line_Item/QtyCUsPerLU_InInvoiceUOM`: Number of CUs in the respective LU, in the invoice unit of measure `Packing/Line_Item/DesadvLine/InvoicingUOM/X12DE355`
    - This value is sometimes more interesting for the DESADV recipient, especially when the DESADV is to be reconciled with the INVOIC later.
