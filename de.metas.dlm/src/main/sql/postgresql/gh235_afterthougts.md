

I found this sentence [here](http://use-the-index-luke.com/sql/explain-plan/postgresql/filter-predicates):

```
Index filter predicates give a false sense of safety; even though an index is used, the performance degrades rapidly on a growing data volume or system load.
```

Which promted my to read further, so I read some more through [http://use-the-index-luke.com](http://use-the-index-luke.com).

And now I believe that we don't really need the partial indices that create there:
* i'm pretty sure that postgresql will not generally try to load the whole (full) index and do a seq scan on the table if that's not possible.
* we do need the "full" indices anyways, because otherways some selects like the one done by the C_Invoice_Cadidate window won't work anymore.

IMHO, what we need to do instead of adding partial indices is to understand how indexing works and consider augmenting our existing indeces.
First concrete questions: if we always have WHERE DLM_Level=0 in any where clause:
* should we do anything about any other index, like adding the DLM_Level column as 2nd index column?
* assuming that the answer is "Y" do we actually need a "dedicated" index on DLM_Level?


Additional note: I also tried (https://www.postgresql.org/docs/9.5/static/sql-cluster.html](https://www.postgresql.org/docs/9.5/static/sql-cluster.html)
It blocks the DB..still might be usefull if we do it once a month..i think we can't know it's wothwhile without trying&measuring.

TODO: try it out again when we have postgresql 9.5, but this time
* try without partial indices but with adding DLM_Level as last column to each existing index (regexp-replace)
* try once again with "current_setting(...)" in the where clause

To read:
http://use-the-index-luke.com/sql/anatomy
http://use-the-index-luke.com/sql/testing-scalability/data-volume
http://use-the-index-luke.com/sql/explain-plan/postgresql