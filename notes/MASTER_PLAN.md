# System Design Mastery Plan — LLD + HLD + Interview Skills

Target: Google SDE2/SDE3-level system design. Built to be *remembered*, not just read.
Copy this file into your repo (`notes/MASTER_PLAN.md`) or paste it into Google Docs.

---

## 0. How this plan works (read once, follow always)

### The 5-step loop for EVERY topic
1. **Predict** — before learning, write 3 lines: "How would I solve this?" (wrong guesses make memory stick)
2. **Learn** — one short concept explanation, one diagram
3. **Build** — code it (LLD) or sketch + prototype it (HLD)
4. **Break** — force a failure (race condition, crash, duplicate message). Write what happened in the **Mistake Log**
5. **Teach back** — explain it in 5 sentences out loud, as if to a friend, with no notes

### Revision schedule (spaced repetition)
Revisit every topic on **day 1 → day 3 → day 7 → day 21 → day 60**.
Each revisit = 5 minutes: cover the notes, answer the "interview questions" from memory, then check.

### Three notebooks (all inside the repo)
| File | Purpose |
|---|---|
| `notes/mistake-log.md` | Every bug/wrong assumption: *what I thought → what happened → rule I learned* |
| `notes/cheatsheet.md` | One page per module: diagram + 5 bullets + trade-offs |
| `notes/questions.md` | Interview questions with your own answers, rewritten after each revision |

### Memory tools used throughout
- **One diagram per concept** (draw it by hand, then redraw from memory)
- **Trade-off pairs**: always write "X is better when… / Y is better when…"
- **Real-world anchor**: tie every concept to a product you know (Redis → Instagram likes, Kafka → Uber trips)
- **Failure first**: ask "what breaks?" before "how does it work?"

---

## 1. What was missing from the original index (the "bias" fix)

The original index is strong on **transactions, booking, Kafka, payments**. It is light or missing on:

| Gap | Added in |
|---|---|
| Low-Level Design (OOP, SOLID, patterns, class design) | Track A, B |
| Concurrency in code (threads, locks, thread pools, `ConcurrentHashMap`) | Track A |
| Microservices vs monolith, API gateway, service discovery | Module H4 |
| Auth (OAuth2, JWT, sessions), security basics, multi-tenancy | Module H9 |
| Observability: logs, metrics, tracing, SLO/SLI/SLA, alerting | Module H8 |
| Deployment: CI/CD, blue-green, canary, feature flags, rollbacks | Module H8 |
| Real-time: WebSockets, SSE, long-polling, WebRTC basics | Module H6 |
| Data pipelines: batch vs stream, MapReduce, Spark/Flink basics | Module H7 |
| Specialized data: time-series, geospatial (geohash/quadtree), graph | Module H3 |
| Storage engines: B-Tree vs LSM tree, WAL, compaction | Module H3 |
| Probabilistic structures: Bloom filter, HyperLogLog, count-min sketch | Module H5 |
| Consensus: Raft/Paxos, leader election, Zookeeper/etcd | Module H5 |
| Google-specific papers: GFS, Bigtable, MapReduce, Spanner, Borg | Track D |
| CDN, edge caching, DNS-based routing | Module H2 |
| Cost awareness and capacity planning | Module H8 |
| Behavioral prep ("Googleyness"), mock interview routine | Track E |

---

## 2. Roadmap overview (24 weeks, ~10 hrs/week; DSA is in a separate session)

| Phase | Weeks | Focus | Output |
|---|---|---|---|
| 1 | 1–4 | OOP + SOLID + core patterns + Java concurrency | 4 LLD projects |
| 2 | 3–10 | HLD fundamentals (overlaps phase 1) | cheatsheets for H1–H5 |
| 3 | 7–16 | LLD ladder + paired HLD designs | 12 more projects + 8 HLD docs |
| 4 | 14–20 | Advanced distributed systems + Google papers | notes on 5 papers |
| 5 | 18–24 | Mock interviews, behavioral, polish | 10 recorded mock designs |

Rule: **never more than 2 new concepts per session.** Depth beats coverage.

---

## 3. TRACK A — LLD Foundations

### A1. OOP and design principles
- Classes, objects, encapsulation, abstraction, inheritance, polymorphism
- Composition over inheritance
- **SOLID** (memory hook: *"Some Old Lions Introduce Dangerous"*)
  - **S**ingle Responsibility — one reason to change
  - **O**pen/Closed — extend without editing
  - **L**iskov Substitution — subtypes must behave like parents
  - **I**nterface Segregation — small interfaces
  - **D**ependency Inversion — depend on abstractions
- DRY, KISS, YAGNI, Law of Demeter

### A2. Design patterns (learn the *problem* first, then the pattern)

| Pattern | Problem it solves | Where you'll use it |
|---|---|---|
| Strategy | swap algorithms at runtime | pricing, rate limiter, payment methods |
| Factory / Abstract Factory | create objects without hardcoding the class | notifications, vehicles |
| Builder | many optional fields | requests, configs |
| Singleton | exactly one instance (use carefully) | logger, config |
| Observer | notify many when state changes | notifications, stock alerts |
| State | behavior changes with state | booking, vending machine, elevator |
| Command | turn actions into objects (undo/queue) | text editor, job scheduler |
| Decorator | add behavior by wrapping | logging, caching layers |
| Adapter | make incompatible interfaces work | third-party payment gateways |
| Chain of Responsibility | pass a request along handlers | logger levels, middleware |
| Template Method | fixed skeleton, variable steps | report generation |
| Repository | hide the data store | every project |

### A3. Concurrency in code (Java)
- Thread, Runnable, ExecutorService, thread pools
- `synchronized`, `ReentrantLock`, `ReadWriteLock`, `StampedLock`
- `volatile`, atomics (`AtomicInteger`, CAS)
- `ConcurrentHashMap`, `BlockingQueue`, `CompletableFuture`
- Deadlock, livelock, starvation, visibility, happens-before
- **Lab:** fix the double-booking bug in your ticket booking project three ways: `synchronized`, per-seat lock, atomic compare-and-set

### A4. Code quality
- Clean naming, small methods, immutability
- Exceptions: checked vs unchecked, custom exceptions
- Unit tests (JUnit), mocking, testing concurrency
- UML: class, sequence, state diagrams (hand-drawn is fine)

---

## 4. TRACK B — LLD Project Ladder (one folder per project in the repo)

Each project: **requirements → class diagram → code → tests → concurrency test → README with "what broke"**.

| # | Project | Patterns / concepts | The "twist" (do this to learn) |
|---|---|---|---|
| 1 | Ticket booking (BookMyShow) | State, Repository, locks | 100 threads book 1 seat. Exactly one must win |
| 2 | Parking lot | Strategy, Factory | add EV charging without editing existing classes |
| 3 | LRU / LFU cache | data structures, generics | make it thread-safe, then make it faster |
| 4 | Rate limiter | Strategy | token bucket vs sliding window, 1000 req/s test |
| 5 | Elevator system | State, scheduling | add a priority/VIP lift |
| 6 | Splitwise | graph, Observer | minimize number of settlement transactions |
| 7 | Hotel / airline reservation | date-range conflict, locks | overlapping booking race |
| 8 | Notification service | Factory, Observer, retry | add a new channel with zero changes elsewhere |
| 9 | Logger framework | Chain of Responsibility, Singleton | async logging with a queue |
| 10 | Vending machine / ATM | State | invalid-transition handling |
| 11 | Chess / Tic-Tac-Toe | OOP modelling | add undo with Command |
| 12 | In-memory key-value store | TTL, concurrency | expiry thread, snapshots |
| 13 | Food delivery order system | State, Strategy | driver assignment strategies |
| 14 | Cab booking (Uber LLD) | Strategy, Observer | nearest-driver matching |
| 15 | Library / inventory management | Repository, search | add reservations and fines |
| 16 | Job scheduler | Command, priority queue | retries and cron-like triggers |
| 17 | Pub/sub (mini Kafka) | queues, consumer groups | offsets and replay |
| 18 | Mini Redis | hash map, TTL, commands | persistence to disk |
| 19 | Social feed (LLD) | Observer, pagination | ranking strategy |
| 20 | Video streaming (YouTube LLD) | see Track C | upload pipeline + watch history |

Build in this order. Do not skip the twist: that is where the learning is.

---

## 5. TRACK C — Paired designs: LLD *and* HLD for the same system

Learn each system twice: first the **classes** (inside one service), then the **architecture** (across services).

| System | LLD focus (code) | HLD focus (architecture) |
|---|---|---|
| **YouTube** | `Video`, `User`, `Channel`, `Comment`, upload state machine, view-count service | chunked upload, transcoding pipeline (queue + workers), object storage, CDN, metadata DB, recommendations |
| **Instagram feed** | `Post`, `Follow`, feed strategy (push/pull) | fan-out on write vs read, hybrid for celebrities, feed cache, media storage |
| **Uber** | `Rider`, `Driver`, `Trip`, matching strategy, trip state machine | geospatial index (geohash/quadtree), location updates, matching service, surge pricing |
| **WhatsApp / chat** | `Message`, `Conversation`, delivery states | WebSockets, presence, message ordering, offline queue, group fan-out |
| **Movie ticket booking** | seat state machine, hold/expiry, locks | seat hold with TTL, payment saga, outbox, scaling during a big release |
| **Payment system** | payment state machine, idempotent processor | idempotency keys, ledger, reconciliation, retries, refunds |
| **E-commerce (Amazon)** | cart, inventory, order state | catalog search, inventory consistency, checkout saga, flash-sale handling |
| **Notification system** | channel strategies, templates, retry | queue per priority, rate limiting per user, DLQ, preferences |
| **URL shortener** | encoder, collision strategy | ID generation, cache, read-heavy scaling, analytics |
| **Rate limiter** | token bucket classes | distributed counters in Redis, race conditions, per-region limits |
| **Search / typeahead** | trie, ranking | inverted index, sharding, autocomplete cache |
| **Distributed file storage (Drive)** | file/folder tree, permissions | chunking, dedup, metadata store, sync, sharing |
| **Job scheduler** | task, retry policy | leader election, partitioned queues, exactly-once-ish execution |
| **Food delivery** | order state machine | restaurant search, driver dispatch, ETA estimation |
| **Google-style: Web crawler** | URL frontier, politeness | distributed crawling, dedup with Bloom filter, storage |
| **Google-style: Distributed cache** | eviction policy | consistent hashing, replication, hot keys |

**Process for each:** requirements → estimates → APIs → data model → LLD classes → HLD diagram → bottlenecks → failures → trade-offs → 5-minute spoken walkthrough.

---

## 6. TRACK D — Google-specific depth

Read the summaries first, then the papers. One paper per week in phase 4.

| Paper / topic | Why it matters |
|---|---|
| **MapReduce** | batch processing model, fault tolerance by re-execution |
| **GFS** (Google File System) | big-file storage, chunk servers, master metadata |
| **Bigtable** | wide-column store, LSM tree, tablets |
| **Spanner** | globally consistent SQL, TrueTime, external consistency |
| **Borg / Kubernetes** | scheduling and cluster management |
| **Dynamo** (Amazon) | eventual consistency, consistent hashing, quorum |
| **Raft** | consensus made understandable |
| **Kafka design doc** | log-based messaging |

For each paper write: *problem → key idea → trade-off → one interview question it answers*.

---

## 7. TRACK H — HLD curriculum (your original index, reorganized and expanded)

Items marked **(+)** are new.

### H1. Foundations
- What is system design, functional vs non-functional requirements
- Scalability, availability, reliability, latency, throughput
- Vertical vs horizontal scaling, stateless vs stateful
- Back-of-envelope estimation **(+ numbers to memorize, see section 11)**
- SLI / SLO / SLA, "nines" of availability **(+)**
- Interview framework (section 9)

### H2. Networking and APIs
- HTTP/1.1, HTTP/2, HTTP/3, TCP vs UDP, DNS, TLS, connection pooling, keep-alive
- REST, resources, methods, status codes, pagination (offset vs cursor), versioning, idempotent APIs
- **(+)** gRPC vs REST vs GraphQL
- Load balancers: L4 vs L7, algorithms, health checks, sticky sessions
- **(+)** CDN, edge caching, anycast, DNS routing
- **(+)** API gateway, reverse proxy, service mesh basics

### H3. Databases and storage
- SQL vs NoSQL, keys, normalization, joins, indexes, B-Tree, EXPLAIN, N+1
- Replication, replication lag, read/write splitting
- Partitioning, sharding, shard keys, hot partitions, rebalancing, cross-shard queries
- **(+)** Storage engines: B-Tree vs **LSM tree**, WAL, compaction
- **(+)** NoSQL types: key-value, document, wide-column, graph, time-series
- **(+)** Geospatial indexing: geohash, quadtree, S2
- Object storage: S3-style, presigned URLs, multipart upload
- Search: inverted index, ranking, autocomplete, Elasticsearch

### H4. Architecture styles
- **(+)** Monolith vs microservices vs modular monolith
- **(+)** Service discovery, config management, sidecars
- **(+)** Domain-driven design basics: bounded contexts
- **(+)** CQRS and event sourcing (when NOT to use them)

### H5. Caching and distributed fundamentals
- Cache-aside, read/write-through, write-back, TTL, invalidation, stampede, penetration, avalanche
- Redis: data structures, TTL, locks, counters, rate limiting, clustering
- Distributed systems: partial failure, network partitions, clock problems
- CAP, **(+)** PACELC, consistency models (strong, eventual, causal, read-your-writes)
- Distributed IDs: UUID, Snowflake, sequences
- Distributed locks: Redis locks, leases, fencing tokens
- Leader election, split brain, **(+)** Raft/Paxos intuition, ZooKeeper/etcd
- Consistent hashing, virtual nodes
- **(+)** Probabilistic structures: Bloom filter, HyperLogLog, count-min sketch
- **(+)** Gossip protocols, quorum reads/writes, vector clocks

### H6. Messaging, events, real-time
- Sync vs async, queue vs topic, backpressure, retry, DLQ, ordering
- Kafka: topics, partitions, consumer groups, offsets, rebalancing, retention, delivery semantics
- RabbitMQ: exchanges, routing, acks
- Event-driven: events vs commands, choreography vs orchestration, saga, outbox, idempotent consumers
- **(+)** Real-time delivery: WebSockets, SSE, long polling, push notifications

### H7. Data processing
- **(+)** Batch vs stream, MapReduce, Spark/Flink basics
- **(+)** Lambda vs Kappa architecture
- **(+)** Data warehouse vs data lake, ETL/ELT
- **(+)** Analytics counters at scale (approximate counting)

### H8. Reliability, performance, operations
- Timeouts, retries, backoff, jitter, circuit breaker, bulkheads, graceful degradation
- High availability: redundancy, failover, active-active/passive, multi-AZ, multi-region
- Disaster recovery: backup, RPO, RTO
- Scalability patterns: auto-scaling, queue-based scaling, load shedding
- Rate limiting: fixed/sliding window, token/leaky bucket, distributed
- Performance: p50/p95/p99, profiling, capacity planning, load testing
- **(+)** Observability: logs, metrics, traces, dashboards, alerts
- **(+)** Deployments: CI/CD, blue-green, canary, feature flags, rollback, database migrations
- **(+)** Cost awareness: storage tiers, egress, compute vs memory trade-offs

### H9. Security and identity **(+ entire module)**
- Authentication vs authorization, sessions vs JWT, OAuth2/OIDC flow
- Password storage, rate-limiting login, API keys
- Encryption at rest and in transit, secrets management
- Multi-tenancy, RBAC/ABAC, audit logs
- Common attacks: injection, CSRF, XSS, DDoS (one line each + defense)

### H10. Transactions and concurrency (your Lesson 2, kept in full)
- Race conditions, atomic updates, transactions, ACID
- Pessimistic vs optimistic locking, lock granularity, deadlocks, lock ordering
- Isolation levels, dirty/non-repeatable/phantom reads, MVCC
- Seat holds with expiry, idempotency, distributed transactions
- Saga/compensation, transactional outbox, retry/backoff/DLQ, payment state machines
- **(+)** Two-phase commit and why people avoid it
- **(+)** Write skew, lost updates, serializable snapshot isolation

---

## 8. Weekly schedule template (10 hrs)

| Day | Time | Activity |
|---|---|---|
| Mon | 1.5h | New concept + predict + learn |
| Tue | 1.5h | Build (LLD code or HLD prototype) |
| Wed | 1h | Break it + mistake log |
| Thu | 1.5h | Paired design / diagram from memory |
| Fri | 1h | Revision slots (day 1/3/7/21 topics) |
| Sat | 2.5h | Mock design (45 min timed) + review |
| Sun | 1h | Weekly summary + plan next week |

DSA runs in its own session: 4–5 problems per week, same revision cadence.

---

## 9. TRACK E — Interview skills

### The 45-minute HLD framework
| Minutes | Step | What to say |
|---|---|---|
| 0–5 | Clarify requirements | users, features, scale, what's out of scope |
| 5–10 | Estimate | QPS, storage, bandwidth |
| 10–15 | APIs + data model | endpoints, tables/keys |
| 15–30 | High-level diagram | components, data flow |
| 30–40 | Deep dive | the hardest 1–2 parts (interviewer picks or you pick) |
| 40–45 | Bottlenecks, failures, trade-offs | "if X dies…", "at 10x scale…" |

### The 45-minute LLD framework
1. Clarify requirements and entities (5 min)
2. Identify classes, relationships, key methods (10 min)
3. Pick patterns *only where they pay off* (5 min)
4. Write core code (20 min)
5. Concurrency, extensibility, tests (5 min)

### Behavioral ("Googleyness")
Prepare **8 stories** in STAR format covering: ownership, conflict, failure, ambiguity, leadership without authority, learning fast, helping a teammate, a technical decision with trade-offs.

### Mock routine
- Weekly: 1 timed design out loud, recorded
- Play it back, note filler words, missing steps, unanswered "what if"
- Monthly: swap with a peer or use a mock-interview service

---

## 10. Per-lesson template (copy for every topic)

```
# <Topic>
Predict: (my guess before learning)
One-liner: 
Diagram: 
How it works (5 bullets):
Trade-offs: X is better when… / Y is better when…
Real-world example:
What breaks (failure modes):
My mistake (from the lab):
Interview Qs + my answers:
Revision dates: D1 __ D3 __ D7 __ D21 __ D60 __
```

---

## 11. Numbers to memorize (cheatsheet)

| Operation | Approx. time |
|---|---|
| L1 cache reference | 0.5 ns |
| Main memory reference | 100 ns |
| SSD random read | 100 µs |
| Round trip inside a datacenter | 0.5 ms |
| Disk seek | 10 ms |
| Cross-continent round trip | 100–150 ms |

| Rule of thumb | Value |
|---|---|
| Seconds in a day | ~86,400 (≈ 10^5) |
| 1M requests/day | ≈ 12 req/s |
| 100M requests/day | ≈ 1,200 req/s |
| Availability 99.9% | ≈ 8.8 hours downtime / year |
| Availability 99.99% | ≈ 53 minutes downtime / year |
| 1 char | 1 byte; 1 KB text ≈ 1,000 chars |

Estimation recipe: **users → actions/day → QPS → peak (×2–5) → storage/day → ×365 × replication**.

---

## 12. Repo structure

```
system-design-mastery/
├── README.md                 progress tracker (section 13)
├── lld/
│   ├── 01-ticket-booking/    README + src + tests + "what broke"
│   └── ...
├── hld/
│   ├── 01-url-shortener/     requirements, estimates, APIs, diagram, trade-offs
│   └── ...
├── papers/                   one summary per paper
└── notes/
    ├── MASTER_PLAN.md        this file
    ├── mistake-log.md
    ├── cheatsheet.md
    └── questions.md
```

---

## 13. Progress tracker

### Track A — LLD foundations
- [ ] OOP + SOLID
- [ ] Creational patterns (Factory, Builder, Singleton)
- [ ] Behavioral patterns (Strategy, Observer, State, Command, Chain)
- [ ] Structural patterns (Decorator, Adapter)
- [ ] Java concurrency basics
- [ ] Testing and UML

### Track B — LLD projects (check when README + tests + twist are done)
- [ ] 1 Ticket booking  - [ ] 2 Parking lot  - [ ] 3 LRU cache  - [ ] 4 Rate limiter
- [ ] 5 Elevator  - [ ] 6 Splitwise  - [ ] 7 Reservation  - [ ] 8 Notifications
- [ ] 9 Logger  - [ ] 10 Vending machine  - [ ] 11 Chess  - [ ] 12 KV store
- [ ] 13 Food delivery  - [ ] 14 Cab booking  - [ ] 15 Library  - [ ] 16 Job scheduler
- [ ] 17 Pub/sub  - [ ] 18 Mini Redis  - [ ] 19 Social feed  - [ ] 20 YouTube LLD

### Track H — HLD modules
- [ ] H1 Foundations  - [ ] H2 Networking/APIs  - [ ] H3 Databases/storage
- [ ] H4 Architecture styles  - [ ] H5 Caching + distributed  - [ ] H6 Messaging/real-time
- [ ] H7 Data processing  - [ ] H8 Reliability/ops  - [ ] H9 Security  - [ ] H10 Transactions

### Track C — Paired designs
- [ ] YouTube  - [ ] Instagram  - [ ] Uber  - [ ] WhatsApp  - [ ] Ticket booking
- [ ] Payments  - [ ] E-commerce  - [ ] Notifications  - [ ] URL shortener  - [ ] Rate limiter
- [ ] Search  - [ ] Drive  - [ ] Job scheduler  - [ ] Food delivery  - [ ] Crawler  - [ ] Distributed cache

### Track D — Papers
- [ ] MapReduce  - [ ] GFS  - [ ] Bigtable  - [ ] Spanner  - [ ] Borg  - [ ] Dynamo  - [ ] Raft

### Track E — Interview
- [ ] 8 behavioral stories  - [ ] 10 mock HLD  - [ ] 10 mock LLD  - [ ] Final review

---

## 14. Interview question bank (answer these from memory during revision)

**Fundamentals**
- Why Kafka? Why Redis? Why SQL instead of NoSQL (and the reverse)?
- How does this scale to 100M users? What's the bottleneck?
- How do you scale reads? Writes? How do you avoid a hot partition?

**Failure**
- What if this server dies? What if Kafka is down? What if the DB goes down?
- What happens during a regional outage?
- What if the payment service is slow? What if a message is processed twice?
- How do you recover from partial failure?

**Correctness**
- How do you prevent two users booking the same seat?
- Where should the transaction boundary be?
- How do you handle duplicate requests and retries?
- Strong vs eventual consistency: which here, and why?

**LLD**
- Which pattern here, and what would break without it?
- How do you add a new payment method without changing existing code?
- How would you make this class thread-safe?
- How would you test this?

**Meta**
- Which trade-off did you make, and what would make you reverse it?
- What would you build first with 2 engineers and 2 weeks?

---

## 15. Final goal

By the end you can:
- Design any common system end to end in 45 minutes, with APIs, data model, diagram, scaling and failure handling
- Write clean, extensible, thread-safe LLD code under time pressure
- Explain every trade-off and defend each choice against follow-ups
- Recall the key concepts from memory, because you built, broke, and taught each one
