API to support (low-level) storage queries.

Usage scenarios: 

* query storage on a locator and attribute level, based on `M_HU`/`M_HU_Storage`
* find out which shipment schedules need updating, based on changed `M_HU`/`M_HU_Storage` changes.

Don't mix up with `MD_Stock` which is on a higher aggregation (warehouse)-level.
