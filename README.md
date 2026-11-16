# PROG2004 Assessment 1 - Health Appointment System

Jian Mao | 25259162 | PROG2004 Object Oriented Programming

## What it does

This is a console simulation of the back end of a clinic appointment system.
It creates health professionals, prints their details, books appointments, then
filters, sorts and cancels them. The brief says not to build a user interface,
login or database, so none of that is here.

The main method runs everything and prints the results, including the bookings
that have to be rejected.

## How to run

- In VS Code: `Ctrl+Shift+B` builds, `F5` runs the demonstration.
- PowerShell: `.\scripts\build.ps1` then `.\scripts\run.ps1`.
- Or just double-click `run.bat`.

Needs JDK 11 or later (tested on JDK 21). No other libraries.

## Files

All classes are in `src`, in the default package, because the brief asks for
`AssignmentOne` directly in `src`.

| File | What it is |
| --- | --- |
| `HealthProfessional` | abstract base class: id, name, working days |
| `Doctor` | abstract class for doctors: registration number and prescribe() |
| `GeneralPractitioner` | a GP, plus whether they take new patients |
| `Specialist` | a doctor with a specialty, e.g. cardiology |
| `Nurse` | takes blood samples, has a ward |
| `Dietitian` | has a dietary focus |
| `Patient` | name and mobile number |
| `Appointment` | patient + professional + time, sortable |
| `ClinicSchedule` | opening hours, closing hours and slot length |
| `AppointmentManager` | the list of appointments and all the operations |
| `AssignmentOne` | the demonstration |

`sample-output.txt` is one full run, if you want to see the output without
running it.

## Design choices

**Encapsulation.** Every field in `HealthProfessional` is private and final, and
the constructor checks the id, the name and the roster. There are no setters.
`getWorkingDays()` returns a copy of the set, otherwise another class could
empty a professional's roster after it was created.

**Inheritance.** GP and Specialist both need a registration number and the
ability to prescribe, so that goes in `Doctor`. Nurse and Dietitian extend
`HealthProfessional` directly, because a nurse should not inherit prescribing.
Subclasses call `super(...)` first and then add their own details in
`toString()`.

**Polymorphism.** All eight professionals are kept in one
`List<HealthProfessional>` and printed in one loop. The loop calls
`performDuty()` and each object runs its own version. `Appointment` also stores
the professional as `HealthProfessional`, so one appointment class works with
every type.

**Abstraction.** `HealthProfessional` is abstract: there is no such thing as a
health professional with no specialty. The two abstract methods force every
subclass to provide a role and a duty.

**The schedule.** `ClinicSchedule` keeps the opening time, the closing time and
the slot length in one place. Everything else asks it whether a time is a valid
slot, so changing the clinic hours is one edit.

**The collection.** `AppointmentManager` uses an `ArrayList`. Order matters for
the sorted view, and two appointments at the same time for different
professionals are allowed. An appointment is already unique by professional and
time, so a `HashMap` was not needed.

## The four pillars, quickly

| Pillar | Where |
| --- | --- |
| Encapsulation | `HealthProfessional`, `Patient`, `Appointment` |
| Inheritance | `Doctor` → `GeneralPractitioner` / `Specialist` |
| Polymorphism | the `List<HealthProfessional>` loop, `Comparable` |
| Abstraction | `abstract class HealthProfessional`, `ClinicSchedule` |

## Assumptions

- Bookings are only for the current day, so an appointment stores a time but no
  date. The working-day check uses `LocalDate.now()`.
- The clinic runs 08:00 to 16:00 in 30 minute slots, so the last start time is
  15:30.
- A mobile number is ten digits starting with 04. Spaces, dashes and a +61
  prefix are accepted.
- For the demonstration to work on any day, the created professionals work
  Monday to Friday plus today. A locum who does not work today is used to show
  the rejection.

## Limitations

- Two patients can still book the same professional at different times. The
  system does not model patient availability.
- No waiting list, no recurring appointments and no cancellation history,
  because the brief does not ask for them.

## Submission

- Repository: <https://github.com/Mao3404553077/A1-JianMao-25259162>
- WakaTime report: `A1-JianMao-25259162-WakaTime.png`
- Video and GenAI declaration are in the MySCU submission comments.
