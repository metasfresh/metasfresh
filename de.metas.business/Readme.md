
This module contains a general collection of business logic that used to be located in adempiere.base.

It does *not* cotnain any "core" logic such as PO, IQueryBL, InterfaceWarapperHelper etc.

Note that in future we might move additional business logic from base into this package, e.g. if there is some code this we want to run async, but can't do so while it is in base.

We might also in future dissolve swat and move parts of the swat-code into this project
