# Pattern 01: Builder (+ constructors, `super`, required vs optional, Director)

**Time:** about 20 minutes. **Domain:** vehicles.
**Interview prompt:** *"Design a Car. Brand, model and engine are mandatory; mileage, price and colour are optional. Cars are vehicles. Toyota wants ready-made presets."*

```
Vehicle (parent)          wheels, registrationNo
   ▲ extends (super)
Car (child + Builder)     brand, model, engine, mileage, price, color
   ▲ uses Car.Builder
ToyotaFactory (Director)  knows the Toyota presets
```

```bash
mvn test
```

---

## Step 1: `step1_telescoping`, feel the pain
Write `Car` using **only constructors** (no parent yet):
- `brand`, `model` (String), `engine` (String): **required**
- `mileage` (double): optional, default `0`
- `price` (long): optional, default `0`
- `color` (String): optional, default `"WHITE"`

Tasks:
- [ ] One constructor per combination, each chaining with `this(...)` to the full one (the **designated constructor**).
- [ ] In `Main`, create a car where you **swap mileage and price by mistake**. Does it compile?

**Write down:** what is hard to read at the call site?

---

## Step 2: `step2_builder`, the fix
Rewrite `Car` with a **Builder**:
- [ ] `EngineType` enum: `PETROL, DIESEL, ELECTRIC, HYBRID`.
- [ ] `Car` is **immutable**: `final` fields, no setters, `private` constructor taking the Builder.
- [ ] Required `brand, model, engine` go in the Builder's constructor; optional fields are methods returning `this`.
- [ ] Defaults live **only** in the Builder.
- [ ] `build()` validates:
  - Required values are not null or blank.
  - `price >= 0`.
  - `mileage >= 0`.
  - An `ELECTRIC` car must not have a mileage in km/l (decide your own rule and write it down).

**Write down:** why does a rule that uses **two** fields go in `build()`?

---

## Step 3: `step3_inheritance`, `Vehicle` + `super`
- [ ] `Vehicle` (parent): `registrationNo` (**required**), `wheels` (int, **private**).
- [ ] `Vehicle` has **one** constructor that sets its fields (designated constructor).
- [ ] `Car extends Vehicle`. Car's constructor calls `super(...)` **first** with `wheels = 4`, then sets the car fields.
- [ ] Add `registrationNo` to `Car.Builder` as a required value.
- [ ] Keep one **old** Car constructor marked `@Deprecated` that is a **one-line** forward to the Builder.
- [ ] `Vehicle.describe()` returns basic info. `Car` overrides it and reuses `super.describe()`.

**Write down:**
- What happens if `Car` does not call `super(...)`?
- Can `Car` read `wheels` directly? What must it use instead?

---

## Step 4: `step4_director`, `ToyotaFactory`
- [ ] `ToyotaFactory` is **not** a subclass of `Car`. It **uses** `Car.Builder`.
- [ ] Methods like `corolla(registrationNo)` and `fortuner(registrationNo)` return a `Car` with Toyota presets.
- [ ] A caller can still build a fully custom car with `Car.Builder` directly.

**Write down:** why is Toyota a **value** (brand) and not a class `Toyota extends Car`? When *would* a subclass be right?

---

## Step 5: tests (`src/test`)
- [ ] Unset optional fields get defaults.
- [ ] Missing required values throw.
- [ ] Invalid combinations throw in `build()`.
- [ ] The deprecated constructor and the Builder produce the **same** car.
- [ ] A `Car` has the parent's values (set via `super`) **and** its own.
- [ ] `ToyotaFactory.corolla(..)` returns brand `Toyota` with the preset values.

---

## Bonus (interview level)
- [ ] Add `Bike extends Vehicle` (2 wheels) with its own Builder. Both Builders repeat `registrationNo`. Remove the duplication with a **generic self-typed builder**: `Vehicle.Builder<T extends Builder<T>>`. Hint: Effective Java, Item 2.
- [ ] Rewrite with **composition** (`Car` *has* a `Vehicle`) and compare.

## Interview questions to answer in your own words
1. Telescoping constructor: what is it, and why is it bad?
2. Builder vs a big constructor vs setters (JavaBeans): pros and cons?
3. Why are required fields in the Builder's constructor?
4. Why is `Car`'s constructor `private`?
5. What is a designated constructor? Why keep old constructors as `@Deprecated` instead of deleting them?
6. GoF Builder (with Director) vs Effective Java Builder: what's the difference?
7. Subclass vs field: how do you decide?
8. When is Builder **overkill**?

## What broke
_(fill in after you finish: bugs you hit, things that surprised you)_
