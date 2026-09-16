# Upgrade Plan: desktop-tutorial (20260906130251)

- **Generated**: 2026-09-06 13:02:51
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A (not returned by version-control status)

## Available Tools

**JDKs**
- JDK 17.0.7: `C:\Users\sd967\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.7.7-hotspot\bin` (current baseline)
- JDK 25.0.2: `C:\Users\sd967\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.2\bin` (target)

**Build Tools**
- Maven 3.9.15: `C:\Users\sd967\.maven\maven-3.9.15\bin`

## Guidelines

- Upgrade the Java runtime/compiler target to the latest LTS version requested by the user.
- Preserve application behavior and existing security-related dependency pins.

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: `appmod/java-upgrade-20260906130251`
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java runtime/compiler target: 25

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| --------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User requested latest LTS |
| Spring Boot | 3.5.16 | 3.5.16 | Compatible with Java 25; no framework upgrade requested |
| Maven | 3.9.15 | 3.9+ | Compatible and already installed |
| maven-compiler-plugin | 3.14.0 | 3.11+ | Compatible with Java 25 |
| maven-surefire-plugin | 3.0.0 | 3.0+ | Compatible with Java 25; no upgrade required |
| Jackson BOM | 2.21.5 | 2.21.5 | Explicit CVE remediation pin; preserve |
| Log4j BOM | 2.25.5 | 2.25.5 | Explicit CVE remediation pin; preserve |
| Kotlin | Not used | N/A | No Kotlin configuration detected |

## Derived Upgrades

- Set Maven compiler release, source, target, and project Java properties to 25 so compilation and runtime packaging target Java 25.
- Use the installed JDK 25.0.2 for upgraded compilation and tests.
- Keep Spring Boot 3.5.16 and the explicit Jackson/Log4j BOM pins because they are already compatible and the pins are documented as CVE-driven.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| `frontend/desktop-tutorial/pom.xml` | `java.version` | 17 | upgrade | 25 | Java runtime/compiler target |
| `frontend/desktop-tutorial/pom.xml` | `maven.compiler.source` | 17 | upgrade | 25 | Source compatibility |
| `frontend/desktop-tutorial/pom.xml` | `maven.compiler.target` | 17 | upgrade | 25 | Bytecode target |
| `frontend/desktop-tutorial/pom.xml` | `maven.compiler.release` | 17 | upgrade | 25 | Compiler release used by maven-compiler-plugin |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|----------------|--------|
| None | N/A | No Java 25-incompatible source patterns found | No source changes expected | Target-only upgrade; source scan found no internal JDK APIs or removed APIs |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|----------------|--------|
| None | N/A | No application configuration tied to Java 17 | No change | Runtime configuration remains valid on Java 25 |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|----------------|
| None | N/A | No CI/CD or container Java version references found | No change |

### Risks & Warnings

- **Existing Java-version documentation drift**: `TEST_REPORT.md` describes an earlier Java 17 compatibility decision. **Mitigation**: Update only if final validation confirms this report is maintained documentation; do not alter historical test evidence during the runtime upgrade.
- **Runtime behavior outside automated tests**: The project has no detected test classes under the Maven module. **Mitigation**: Run Maven test lifecycle on JDK 25 and perform a clean package; record the absence of test cases as residual validation risk.
- **Security dependency pins**: Jackson 2.21.5 and Log4j 2.25.5 are explicitly documented CVE remediation pins. **Mitigation**: retain both overrides and run the CVE scan after the upgrade.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Confirm the target JDK and Maven installations are available.
  - **Changes to Make**: Use installed JDK 25.0.2 and Maven 3.9.15; no installation required.
  - **Verification**: JDK/Maven inventory; expected both required tools available.

- Step 2: Setup Baseline
  - **Rationale**: Capture pre-upgrade compile and test behavior under Java 17.
  - **Changes to Make**: No project changes.
  - **Verification**: Maven clean compile/test-compile and clean test with JDK 17; record result.

- Step 3: Upgrade Java Target Configuration
  - **Rationale**: Apply the requested Java 25 runtime/compiler target.
  - **Changes to Make**: Apply all Dependency Changes above in `frontend/desktop-tutorial/pom.xml`; preserve all CVE pins and other dependency versions.
  - **Verification**: `mvn clean test-compile -q` with JDK 25; expected successful main and test compilation.

- Step 4: CVE Validation and Fix
  - **Rationale**: Confirm the Java-target-only change does not leave direct dependencies vulnerable.
  - **Changes to Make**: Scan direct dependencies; change only dependency versions required to resolve reported CVEs, preserving existing security pins.
  - **Verification**: CVE scan before/after any fixes and `mvn clean test-compile -q` with JDK 25.

- Step 5: Final Validation
  - **Rationale**: Verify all upgrade success criteria on the target runtime.
  - **Changes to Make**: Resolve any Java 25 compilation or test failures; ensure no temporary TODOs/workarounds remain.
  - **Verification**: `mvn clean test -q` and clean package/coverage validation with JDK 25; expected build success and 100% of discovered tests passing.
