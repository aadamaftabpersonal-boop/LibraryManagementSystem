# Design

## Nouns → Classes
- Items: LibraryItem (abstract), Book, Magazine, ReferenceBook
- Members: Member (abstract), Student, Staff, Faculty
- Loan, Library (coordinator), Catalog, MemberRegistry, LoanRegistry, SimulatedClock
- Capability: Borrowable (interface)

## Verbs → Methods
- add item / member → Library.addItem / addMember
- borrow → Library.borrow(memberId, itemId)
- return → Library.returnItem(itemId)
- advance → Library.advance(days) → SimulatedClock.advance(days)
- search / list loans / overdue / inventory → Library report methods

## Where Each Rule Lives

| Rule                                   | Home                                            |
|----------------------------------------|-------------------------------------------------|
| Max items at once (3 / 4 / 5)          | Student / Staff / Faculty `getMaxItems()`       |
| Loan period (14 / 21 / 30 days)        | Student / Staff / Faculty `getLoanPeriodDays()` |
| Can it be borrowed?                    | Implements `Borrowable` or not                  |
| Fine per day (₹2 book / ₹1 magazine)   | Book / Magazine `getFinePerDay()`               |
| Due day = borrow day + loan period     | `Loan` constructor                              |
| Fine = days late × rate, 0 if on time  | `Loan.fineAsOf(today)`                          |
| Item on loan to only one member        | `LoanRegistry` (map keyed by item ID)           |
| Member limit check                     | `Member.ensureCanBorrow(currentLoans)`          |
| Time only moves via ADVANCE            | `SimulatedClock`                                |

## Failures → Exceptions

| Failure                   | Exception                    | Thrown by                   |
|---------------------------|------------------------------|-----------------------------|
| Unknown item ID           | ItemNotFoundException        | `Catalog.get(id)`           |
| Unknown member ID         | MemberNotFoundException      | `MemberRegistry.get(id)`    |
| Item already on loan      | ItemNotAvailableException    | `Library.borrow`            |
| Item not borrowable       | ItemNotAvailableException    | `Library.borrow`            |
| Member at limit           | BorrowLimitExceededException | `Member.ensureCanBorrow`    |
| Return of item not on loan| ItemNotOnLoanException       | `LoanRegistry.close(itemId)`|

All extend `LibraryException` (unchecked) and are caught once, in `CommandLoop`,
which prints `ERROR <message>` and continues.

## Decisions
- **Loan is a class**: it owns the borrow day and due day, and computes days late and fines.
- **Borrowable is an interface**: borrowing is a capability only some items have.
- **Command pattern**: each command is a class in a map; adding one doesn't change the loop.
- **Library is a Facade** over Catalog, MemberRegistry, LoanRegistry and SimulatedClock.
- **Storage uses HashMap**; each report sorts with its own `Comparator`.
- **BORROW check order**: member → item → borrowable → on loan → limit.
- **Exceptions build their own messages** from the data they receive, so each message is written once.