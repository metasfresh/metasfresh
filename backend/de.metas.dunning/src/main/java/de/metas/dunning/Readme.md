
### Processes

`de.metas.dunning.process.C_Dunning_Candidate_Create`
* AD_Process.Name: "Mahndispo aktualisieren"
* Deletes all unprocessed C_Dunning_candidates and creates new candidates as required

`de.metas.dunning.process.C_Dunning_Candidate_Process`
* AD_Process.Name: "Mahnungen erstellen"
* Processes C_DunningDocs into C_DunningCandidates

`de.metas.dunning.process.C_Dunning_Candidate_SetDunningGrace`
* AD_Process.Name: "Set Dunning Grace"

`de.metas.dunning.process.C_DunningDoc_Process`
* AD_Process.Name: "Mahndokumente fertigstellen"

`de.metas.dunning.invoice.process.C_Dunning_Candidate_MassWriteOff`


`de.metas.dunning.invoice.process.C_Invoice_UpdateAutomaticDunningGrace`


### Documentation

The metasfresh dunning feature works by first creating a dunning candidate in an automatically running process, and then creating the actual dunning document in another, manually started process. 
* To create the candidates, the process `de.metas.dunning.process.C_Dunning_Candidate_Create` is Used
* To create the Dunning Documents, the process `de.metas.dunning.process.C_Dunning_Candidate_Process` is Used


`Process de.metas.dunning.process.C_Dunning_Candidate_Create`

* The process not only creates, but also updates unprocessed candidates by deleting and entirely recreating them. (processed candidates are not deleted)
* It should be included in AD_Scheduler and run daily, but can also be launched from the menu

To define if a dunning candidate for a document shall be created, the Dunning configuration in the Table C_Dunning is used. The used dunning conditions are retrieved from the BPartner of a dunnable document (If null in the BPartner, its taken from its BP Group. If still null, no dunning candidate will be created). Depending on the times a document has already been dunned, the according Dunning level is chosen.

A Candidate will be created if the number of days since the due date of the document is larger or equals the due days in the retrieved dunning level. The dunning grace is not used at this point. Technically, it is possible to configure a dunning level to dun documents that are not due or to ignore invoices that are due, but these configurations were not tested.

A candidate will be created for each level, but only one at a time. This means, if an invoice is due since 30 days and there are 2 dunning levels - level 1 (10 days over due) and level 2 (20 days over due) - only one candidate with level 1 will be created. Once this candidate and its dunning doc are processed, another candidate can be created. The creation of this and other, following candidates can be delayed using the column DaysBetweenDunning. The number entered here defines the number of days (after the processing of the previous dunning document) the Process waits until the next candidate is created.


`Dunning Candidate`

A dunning candidate represents a dunnable document. It is possible to modify such a candidate to postpone the actual dunning, for example by adding a Dunning grace. If the dunning grace is set in the dunning candidate, it will be copied to the invoice and vice versa.

The flag "Processed" tells if a dunning document was created, and the flag "DunningDocProcessed" tells if it was processed.


`Process de.metas.dunning.process.C_Dunning_Candidate_Process`
* The process can be launched from the menu to create dunning documents for all candidates
* The process can be launched from the gearbox in the dunning candidates window to create dunning documents only for the filtered candidates
* The process has a Parameter, that defines if the created dunning documents will be completed.

If the Process is executed, it filters the candidates first. Candidates, which are processed or have the flag "DunningDocProcessed" set, as well as candidates, which have the dunning grace set to a future date, will be sorted out. The other candidates will be used to create an order. If there are multiple candidates with the same BPartner, BPartner_Location, contact, and dunning level will be aggregated into one dunning document.


#### Salvaged from some lagacy docs

Overall architecture
   
* Basic idea for metasfresh
  * Generic for Documents to create Dunning Candidates (similar to invoice/ order candidates, specific candidate processor per doctype)
    * Invoices (dunning candidates openamt, due days)  
    * example Prepay Order (dunning candidates openamt, due days, "reminder for payment")
    * example Purchase Order Vendor (dunning candidates due days delivery, "reminder for delivery")
    * example RMA Vendor (dunning candidates, "reminder for redelivery or credit memo")
  * Window Dunning Candidates
    * diff. filter criteria
    * Process to create Dunnings (specific for each Doctype)
    * Statistics about Dunning created, processed, printed (using/ extending existing Documentprint/ generate Functionality), Dunning Level
    * Dunning window
    * Shows all created dunning/ reminder documents
    * Generic ADempiere window with Print preview/ print ...
    * Dunning lines

<pre>
----------------------------------------
 +----------+                +-----------+        +-----------+        +-----------+        +-----------+
 |          |                |           |        |           |        |           |        |           |
 | ORDER    |--------+-------| DUNNING   |--------| DUNNING   |--------| DUNNING   |--------| DUNNING   |
 |          |        |       | CAND.PROC |        | CAND.     |        | GENERATOR |        | HEADERDOC |
 +----------+        |       |           |        |           |        |           |        |           |
                     |       +-----------+        +-----------+        +-----------+        +---+-------+---+
                     |                                                                          |           |
 +----------+        |                                                                          | LINES     |
 |          |        |                                                                          |           |
 | PURCHASE |--------+                                                                          +-----------+
 | ORDER    |        |
 |          |        |
 +----------+        |
                     |
                     |
 +----------+        |
 |          |        |
 | INVOICE  |--------+
 |          |
 +----------+
----------------------------------------
</pre>
