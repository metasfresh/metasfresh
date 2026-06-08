
## Post-integration cleanup (after PR 24478 is integrated)
- Close PR https://github.com/metasfresh/metasfresh/pull/23792 (superseded by 24478) with a WHY comment linking 24478, and delete its branch `soft_panda_release_FixDuplicateQtyPickedOnReversal`. (User decision 2026-06-08: 23792 not needed once the fix lands in 24478.) PR closing/branch deletion is the human/Tobias's call — surface when 24478 integrates.
- Route the 2 reviewer rule-gap candidates through metasfresh-capturing-learnings (ITrxManager class-field convention; trx-dependency-at-call-site doc) — with user OK.
