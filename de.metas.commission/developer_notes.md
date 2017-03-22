
SalesRep and Customer

* C_Sponsor: Node in the hierarchy tree. note that all sorts of "degeneared" tree are also OK
* a customer is linked via C_Sponsor.C_BPartner_ID
* links to the Salesrep *and* to the parent sponsor node via C_Sponsor_SalesRep records
* C_Sponsor_SalesRep 
  - table used to link C_Sponsors to sales reps and to parent nodes (either or, one ssr record never does both).
  - ValidFrom and ValidTo

Commission events

* old: MCAdvCommissionFactCand
* new: async-packages

Commission "ledger"

* C_AdvCommissionInstance
  - link to customer- und salesrep-sponsor
  - link to "trigger" document line via AD_Table_ID/Record_ID (often C_OrderLine, but can be other records)
  
* C_AdvCommissionFact
  - child records of C_AdvCommissionInstance
  - could have e.g. one record for
    - C_OrderLine (like it's parent C_AdvCommissionInstance)
	- C_InvoiceLine
	- C_AllocationLine (when the invoice was paid)
	- C_AdvCommissionPayrollLine
	- C_AllocationLine (when the commission was paid to the sales rep)
  - assign commission points and/or money to certain events within one commission instance
  - Status: 
    - PR/prognosed (when just the order was made)
	- CP/to compute (when the customer paid)
	- to pay (to the sales rep)
	- ...
	
Commission calculation

* C_AdvCommissionPayroll
* C_AdvCommissionPayrollLine

Commission payment

* C_Invoices
* old: used to be created, when HR_Process was completed (MI CommissionValidator)
* new: system shall create C_InvoicE_candidates from C_AdvCommissionPayrollLines
