-- Drop the view as a first step in moving the IsConfirmedBySupplier flag from C_Order to M_ReceiptSchedule

DROP VIEW IF EXISTS QtyDemand_QtySupply_V
;