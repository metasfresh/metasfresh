# The Loader & Saver Pattern: Architectural Overview

## 1. Purpose and Context

The Loader & Saver pattern was conceived to address a common challenge in legacy systems: transitioning from **record-oriented business logic** to **domain-object-oriented business logic**, while preserving performance, consistency, and the ability to evolve the system gradually.  

In many mature systems, legacy models exist as flat database record objects, directly reflecting table structures. Domain logic is often tightly coupled with these records. This makes it difficult to introduce rich domain objects, aggregate roots, and complex business rules without rewriting large portions of the codebase.

The Loader & Saver pattern serves as a **bridge**, allowing:

- Domain objects to be created, manipulated, and validated.
- Legacy record structures to remain the source of truth for persistence.
- Complex aggregates (root + children + sub-children) to be loaded, modified, and persisted efficiently.
- Fine-grained control over transactional behavior, caching, and overrides.

---

## 2. Core Concepts

### a) Unit of Work

At its heart, the Loader & Saver pattern embodies the **unit-of-work principle**:  
- A single instance represents the lifetime of a “load-modify-save” operation.  
- It maintains a working set of loaded records and their corresponding domain objects.  
- Changes are tracked and persisted only when explicitly committed, ensuring consistency and preventing unnecessary database writes.

### b) Identity Map

Each Loader & Saver instance maintains an **identity map**:  
- Ensures that each record loaded from the database is represented by a single object in memory.  
- Prevents duplication and keeps object references consistent across the aggregate.  
- Enables efficient access to related objects, avoiding N+1 query patterns.

### c) Record Override and Read-Only Support

The pattern allows certain records to be injected as overrides:  
- Useful for reacting to user interface changes that have not yet been persisted.  
- Provides a mechanism to include modified records in domain logic without triggering double persistence.  
- Supports complex scenarios where child records are modified independently but the aggregate root needs to be aware of the change before saving.

### d) Aggregate Awareness

Loader & Saver is **aggregate-aware**:  
- Supports loading a complete aggregate (root + children + sub-children) in a single operation.  
- Enables domain services to operate on a fully consistent view of the data.  
- Decouples the domain logic from the persistence mechanism, allowing rich behavior without being constrained by database schemas.

---

## 3. Architectural Benefits

1. **Safe Domain Logic Execution**  
   - All modifications occur on domain objects representing a consistent aggregate.  
   - Business rules can run against a complete and accurate snapshot of the data.

2. **Thread-Safety and Isolation**  
   - Each operation uses its own Loader & Saver instance.  
   - There is no shared mutable state between concurrent operations.

3. **Flexible Transition Strategy**  
   - Legacy record structures remain intact, minimizing risk.  
   - Domain objects can be introduced incrementally, easing migration from record-oriented BL.

4. **Performance Optimization**  
   - Preloading and caching of aggregates reduces database queries.  
   - Identity map prevents repeated loading of the same records.  

5. **Extensibility**  
   - Supports complex aggregates of arbitrary depth.  
   - Can handle record injection, read-only scenarios, and controlled persistence in a uniform manner.

---

## 4. Philosophical Perspective

The Loader & Saver pattern embodies a few key principles:

- **Isolation over sharing**: Each operation is self-contained to ensure correctness.  
- **Explicit over implicit**: Loading, modifying, and saving are clearly defined steps.  
- **Bridge between worlds**: It decouples legacy persistence from modern domain-driven design without forcing a complete rewrite.  
- **Predictable behavior**: By tracking identity and changes within a single operation, the pattern minimizes surprises, even in complex, multi-level aggregates.

In essence, the Loader & Saver pattern is **not just a technical construct**, it is a **conceptual tool** to manage complexity in evolving systems: providing a clear framework for working with aggregates, bridging legacy and modern approaches, and giving developers confidence that domain rules are applied correctly and efficiently.

---

## 5. Summary

- The Loader & Saver pattern is designed to **enable domain-oriented development on top of legacy record-oriented models**.  
- It provides **unit-of-work semantics, identity mapping, aggregate-awareness, and record override support**.  
- Its main goals are **safe, predictable, efficient, and incremental transition** from record-based to domain-driven design.  
- It emphasizes **isolation, clarity, and explicit handling of data**, allowing developers to reason about business rules in a consistent and maintainable way.
