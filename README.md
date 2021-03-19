# android-content-provider

Sample android studio project to demonstrate Content Provider, Room, LiveData, Kotlin, Coroutines

## Modules:
- app => Content Provider (Owns the actual data in Room)
- samplecp => Normal app which consumes data using Content Resolver
- CPsContracts => Content Provider Contract which also has helper methods to query content provider

## Permissions:
- com.sanjaydevtech.cps.DOMAIN_READ => Read permission
- com.sanjaydevtech.cps.DOMAIN_WRITE => Write permission

## Android Studio Version:
- AS 4.1.2