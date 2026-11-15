# PROG2004 Assessment 1 - Health Appointment System

| Item | Detail |
| --- | --- |
| Unit | PROG2004 Object Oriented Programming |
| Assessment | Assessment 1 (40%, individual) |
| Student | Jian Mao |
| Due | Monday of Week 4, 16 Nov 2026, 11:59 pm |
| Language | Java (no external libraries) |

## 1. What the program does

The program is a scripted console simulation of the back end of a health-service
appointment system. It does not have a user interface, menus, login or data
persistence - the brief explicitly says not to build those. Instead, `main` creates
health professionals of several types, prints their details, models an appointment,
books and manages a collection of appointments, and prints the results of every
operation, including the operations that must fail.

## 2. Project structure

```
A1-jmao13/
├── src/
│   ├── AssignmentOne.java         entry point and the Parts 3, 4 and 6 demonstration
│   ├── HealthProfessional.java    Part 1 - abstract base class
│   ├── Doctor.java                Part 2 - abstract class for doctors (prescribing)
│   ├── GeneralPractitioner.java   Part 2 - required concrete type
│   ├── Specialist.java            Part 2 - concrete type with a specialty
│   ├── Nurse.java                 Part 2 - concrete type that takes blood samples
│   ├── Dietitian.java             Part 2 - fourth concrete type
│   ├── Patient.java               Part 4 - patient name and mobile number
│   ├── Appointment.java           Part 4 - patient + professional + time, Comparable
│   ├── ClinicSchedule.java        Part 5 - the fixed 08:00-16:00 daily schedule
│   └── AppointmentManager.java    Part 5 - booking, filtering, sorting, cancelling
