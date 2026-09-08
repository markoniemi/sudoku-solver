# Sudoku Solver Dependency Update Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Update sudoku-solver project dependencies from 2019-era versions to current stable releases, upgrade Java runtime from 1.8 to 11+, and fix alpha/beta dependency versions.

**Architecture:** Dependency updates will be applied in three phases: (1) audit and version selection with compatibility analysis, (2) pom.xml updates grouped by dependency category (core, logging, testing, plugins), (3) validation via clean build, unit tests, and CI/CD workflow updates. Each phase produces testable state.

**Tech Stack:** Maven 3.x, Java 11+, JUnit 5, SLF4J 2.x stable, Spring Framework 5.3+

**Spec:** None (ad-hoc maintenance). Constraints: maintain Java 11+ compatibility, use only stable (non-alpha/beta) versions, ensure test suite passes, update GitHub Actions to JDK 11+.

---

## File Structure

**Files to modify:**
- `pom.xml` — dependency versions, plugin versions, Java source/target, maven-compiler config
- `.github/workflows/maven.yml` — GitHub Actions JDK version and checkout/setup-java actions

**No new files created.** Plan is configuration-only.

---

## Phase 1: Dependency Audit & Version Selection

### Task 1: Document current versions and identify upgrades

**Files:**
- Reference: `pom.xml` (read-only for this task)

**Interfaces:**
- Produces: mapping of current versions to target versions (text format)

- [ ] **Step 1: Create a dependency audit file locally**

Record current state:

```
CURRENT VERSIONS (sudoku-solver/pom.xml):
- spring-beans: 5.1.9.RELEASE → target: 5.3.33
- spring-core: 5.1.9.RELEASE → target: 5.3.33
- spring-test: 5.1.9.RELEASE → target: 5.3.33
- lombok: 1.18.10 → target: 1.18.30
- commons-lang: 2.6 (DEPRECATED) → target: commons-lang3 3.14.0
- commons-collections: 20040616 (2004!) → target: 3.2.2
- slf4j-api: 2.0.0-alpha0 (ALPHA) → target: 2.0.5
- slf4j-log4j12: 2.0.0-alpha0 (ALPHA) → target: 1.7.36 (use with log4j 1.2.17)
- log4j: 1.2.17 → target: 1.2.17 (EOL, consider logback)
- commons-io: 2.6 → target: 2.15.1
- guava: 28.1-jre → target: 32.1.3-jre
- junit: 4.13-beta-3 (BETA) → target: 4.13.2
- maven-compiler-plugin: 3.1 → target: 3.11.0
- jacoco-maven-plugin: 0.7.1.201405082137 → target: 0.8.10
- plantuml-maven-plugin: 1.1 → target: 1.3.3
- plantuml: 7999 → target: 1.2023.13
- maven-project-info-reports-plugin: 2.9 → target: 3.5.0
- versions-maven-plugin: 1.2 → target: 2.16.2
- maven-dependency-plugin: 2.4 → target: 3.6.1
- maven-javadoc-plugin: 2.10.4 → target: 3.6.3
- umldoclet: 1.0.9 → target: 2.4.8

Java target: 1.8 → target: 11
GitHub Actions: JDK 1.8 → target: 11
```

- [ ] **Step 2: Note compatibility concerns**

SLF4J 2.x is major version bump. Options:
- Option A: Keep log4j 1.2.17 with SLF4J 1.7.36 (stable, minimal risk)
- Option B: Upgrade to SLF4J 2.0.5 with log4j 1.2.17 (bridges exist but less common)
- **Decision: Use Option A** (SLF4J 1.7.36 is stable and well-tested with log4j 1.2)

---

## Phase 2: pom.xml Dependency Updates

### Task 2: Update core Spring dependencies

**Files:**
- Modify: `pom.xml:12-22` (Spring dependencies)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with Spring 5.3.33 for beans, core, test

- [ ] **Step 1: Open pom.xml and locate Spring dependencies (lines 13-22)**

- [ ] **Step 2: Update spring-beans to 5.3.33**

Replace:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-beans</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-beans</artifactId>
    <version>5.3.33</version>
</dependency>
```

- [ ] **Step 3: Update spring-core to 5.3.33**

Replace:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>5.3.33</version>
</dependency>
```

- [ ] **Step 4: Update spring-test to 5.3.33 (line 72)**

Replace:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.1.9.RELEASE</version>
    <scope>test</scope>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.3.33</version>
    <scope>test</scope>
</dependency>
```

- [ ] **Step 5: Verify changes and commit**

Run:
```bash
mvn dependency:tree | head -30
```

Commit:
```bash
git add pom.xml
git commit -m "chore: upgrade Spring Framework to 5.3.33"
```

---

### Task 3: Update commons and utility libraries

**Files:**
- Modify: `pom.xml:23-37` (commons-lang, commons-collections, commons-io, guava)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with commons-lang3, modern commons-collections, commons-io 2.15.1, guava 32.1.3

- [ ] **Step 1: Replace commons-lang 2.6 with commons-lang3 3.14.0**

Replace:
```xml
<dependency>
    <groupId>commons-lang</groupId>
    <artifactId>commons-lang</artifactId>
    <version>2.6</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.14.0</version>
</dependency>
```

**Note:** GroupId and artifactId change from `commons-lang:commons-lang` to `org.apache.commons:commons-lang3`. Code using `org.apache.commons.lang.*` will work; old `org.apache.commons.lang3.*` already exists in codebase.

- [ ] **Step 2: Update commons-collections to 3.2.2**

Replace:
```xml
<dependency>
    <groupId>commons-collections</groupId>
    <artifactId>commons-collections</artifactId>
    <version>20040616</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>commons-collections</groupId>
    <artifactId>commons-collections</artifactId>
    <version>3.2.2</version>
</dependency>
```

- [ ] **Step 3: Update commons-io to 2.15.1**

Replace:
```xml
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.15.1</version>
</dependency>
```

- [ ] **Step 4: Update guava to 32.1.3-jre**

Replace:
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>28.1-jre</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>32.1.3-jre</version>
</dependency>
```

- [ ] **Step 5: Commit**

```bash
git add pom.xml
git commit -m "chore: upgrade commons libraries (lang3, collections, io) and guava"
```

---

### Task 4: Update logging dependencies (SLF4J stable, keep log4j 1.2)

**Files:**
- Modify: `pom.xml:38-52` (SLF4J and log4j)

**Interfaces:**
- Consumes: version mapping and SLF4J decision from Task 1
- Produces: pom.xml with SLF4J 1.7.36, log4j 1.2.17 unchanged

- [ ] **Step 1: Update slf4j-api from 2.0.0-alpha0 to 1.7.36**

Replace:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha0</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.36</version>
</dependency>
```

- [ ] **Step 2: Update slf4j-log4j12 from 2.0.0-alpha0 to 1.7.36**

Replace:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>2.0.0-alpha0</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.36</version>
</dependency>
```

- [ ] **Step 3: Leave log4j unchanged at 1.2.17 (EOL but stable)**

No changes needed. log4j 1.2.17 is EOL but stable and widely used. Future: consider migrating to logback or log4j2.

- [ ] **Step 4: Commit**

```bash
git add pom.xml
git commit -m "chore: fix SLF4J to stable 1.7.36 (replace alpha 2.0.0-alpha0)"
```

---

### Task 5: Update test dependencies (JUnit to stable)

**Files:**
- Modify: `pom.xml:63-75` (JUnit and spring-test already done in Task 2)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with JUnit 4.13.2 (stable, not beta-3)

- [ ] **Step 1: Update junit from 4.13-beta-3 to 4.13.2**

Replace:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13-beta-3</version>
    <scope>test</scope>
</dependency>
```

With:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

- [ ] **Step 2: Verify spring-test already updated to 5.3.33 from Task 2**

Check line ~73. Should show:
```xml
<version>5.3.33</version>
```

- [ ] **Step 3: Commit**

```bash
git add pom.xml
git commit -m "chore: upgrade JUnit to stable 4.13.2 (replace beta 4.13-beta-3)"
```

---

### Task 6: Update lombok

**Files:**
- Modify: `pom.xml:23-27` (lombok)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with lombok 1.18.30

- [ ] **Step 1: Update lombok to 1.18.30**

Replace:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.10</version>
</dependency>
```

With:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
</dependency>
```

- [ ] **Step 2: Commit**

```bash
git add pom.xml
git commit -m "chore: upgrade lombok to 1.18.30"
```

---

### Task 7: Update Maven build plugins

**Files:**
- Modify: `pom.xml:86-115` (maven-compiler-plugin, jacoco, plantuml)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with updated plugin versions and Java 11 source/target

- [ ] **Step 1: Update maven-compiler-plugin to 3.11.0 and set Java source/target to 11**

Replace (lines 86-95):
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <encoding>${project.build.sourceEncoding}</encoding>
    </configuration>
</plugin>
```

With:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>11</source>
        <target>11</target>
        <encoding>${project.build.sourceEncoding}</encoding>
    </configuration>
</plugin>
```

- [ ] **Step 2: Update jacoco-maven-plugin to 0.8.10**

Replace (lines 97-114):
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.7.1.201405082137</version>
    <!-- ... rest stays same ... -->
</plugin>
```

With:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <!-- ... rest stays same ... -->
</plugin>
```

- [ ] **Step 3: Update plantuml-maven-plugin to 1.3.3 and plantuml to 1.2023.13**

Replace (lines 116-145):
```xml
<plugin>
    <groupId>com.github.jeluard</groupId>
    <artifactId>plantuml-maven-plugin</artifactId>
    <version>1.1</version>
    <!-- ... execution config stays same ... -->
    <dependencies>
        <dependency>
            <groupId>net.sourceforge.plantuml</groupId>
            <artifactId>plantuml</artifactId>
            <version>7999</version>
        </dependency>
    </dependencies>
</plugin>
```

With:
```xml
<plugin>
    <groupId>com.github.jeluard</groupId>
    <artifactId>plantuml-maven-plugin</artifactId>
    <version>1.3.3</version>
    <!-- ... execution config stays same ... -->
    <dependencies>
        <dependency>
            <groupId>net.sourceforge.plantuml</groupId>
            <artifactId>plantuml</artifactId>
            <version>1.2023.13</version>
        </dependency>
    </dependencies>
</plugin>
```

- [ ] **Step 4: Commit**

```bash
git add pom.xml
git commit -m "chore: update Maven plugins (compiler 3.11.0, jacoco 0.8.10, plantuml 1.3.3) and Java source/target to 11"
```

---

### Task 8: Update reporting plugins

**Files:**
- Modify: `pom.xml:149-209` (reporting section plugins)

**Interfaces:**
- Consumes: version mapping from Task 1
- Produces: pom.xml with updated reporting plugin versions

- [ ] **Step 1: Update maven-project-info-reports-plugin to 3.5.0**

Replace (lines 152-154):
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-project-info-reports-plugin</artifactId>
    <version>2.9</version>
```

With:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-project-info-reports-plugin</artifactId>
    <version>3.5.0</version>
```

- [ ] **Step 2: Update versions-maven-plugin to 2.16.2**

Replace (lines 167-169):
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
    <version>1.2</version>
```

With:
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
    <version>2.16.2</version>
```

- [ ] **Step 3: Update maven-dependency-plugin to 3.6.1**

Replace (lines 181-183):
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.4</version>
</plugin>
```

With:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.6.1</version>
</plugin>
```

- [ ] **Step 4: Update maven-javadoc-plugin to 3.6.3 and umldoclet to 2.4.8**

Replace (lines 185-208):
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>2.10.4</version>
    <configuration>
        <overview>src/main/java/overview.html</overview>
        <!-- ... commented-out doclet entries ... -->
        <doclet>nl.talsmasoftware.umldoclet.UMLDoclet</doclet>
        <docletArtifact>
            <groupId>nl.talsmasoftware</groupId>
            <artifactId>umldoclet</artifactId>
            <version>1.0.9</version>
        </docletArtifact>
        <!-- ... rest ... -->
    </configuration>
</plugin>
```

With:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.6.3</version>
    <configuration>
        <overview>src/main/java/overview.html</overview>
        <!-- ... commented-out doclet entries ... -->
        <doclet>nl.talsmasoftware.umldoclet.UMLDoclet</doclet>
        <docletArtifact>
            <groupId>nl.talsmasoftware</groupId>
            <artifactId>umldoclet</artifactId>
            <version>2.4.8</version>
        </docletArtifact>
        <!-- ... rest ... -->
    </configuration>
</plugin>
```

- [ ] **Step 5: Commit**

```bash
git add pom.xml
git commit -m "chore: update reporting plugins (info-reports 3.5.0, versions 2.16.2, dependency 3.6.1, javadoc 3.6.3, umldoclet 2.4.8)"
```

---

## Phase 3: Build Validation & Testing

### Task 9: Clean build and run unit tests

**Files:**
- Reference: pom.xml (updated)
- Test: all files in `src/test/java/`

**Interfaces:**
- Consumes: updated pom.xml from Tasks 2-8
- Produces: passing unit test suite, no compilation errors

- [ ] **Step 1: Run clean install with tests**

```bash
cd C:\Users\marko\Documents\Git\sudoku-solver
mvn clean install -DskipTests
```

Expected: BUILD SUCCESS (compiles with no errors)

- [ ] **Step 2: Run unit tests**

```bash
mvn test
```

Expected: All tests PASS. If failures, read error messages and determine if code changes needed (unlikely with just dependency updates).

- [ ] **Step 3: Check build output for deprecation warnings**

```bash
mvn clean install | grep -i "deprecat"
```

Document any deprecations for future cleanup (e.g., log4j is EOL).

- [ ] **Step 4: Commit if all tests pass**

```bash
git add pom.xml
git commit -m "test: verify all unit tests pass after dependency updates"
```

---

### Task 10: Verify code compiles with Java 11

**Files:**
- Build configuration: pom.xml (already updated to Java 11)

**Interfaces:**
- Consumes: pom.xml with Java 11 source/target
- Produces: successful compilation with Java 11

- [ ] **Step 1: Confirm Maven sees Java 11**

```bash
mvn -version
```

Expected output includes `Maven 3.x` and `Java version: 11.x.x`

- [ ] **Step 2: Run full build again**

```bash
mvn clean verify
```

Expected: BUILD SUCCESS. No Java compatibility errors.

- [ ] **Step 3: If successful, no commit needed (already committed in Task 9)**

---

## Phase 4: CI/CD Updates

### Task 11: Update GitHub Actions workflow to use JDK 11

**Files:**
- Modify: `.github/workflows/maven.yml`

**Interfaces:**
- Consumes: Java 11 requirement from pom.xml
- Produces: GitHub Actions workflow using actions/setup-java with JDK 11

- [ ] **Step 1: Update GitHub Actions versions and JDK**

Replace entire content of `.github/workflows/maven.yml`:

```yaml
name: Build
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 11
      uses: actions/setup-java@v4
      with:
        java-version: 11
        distribution: 'temurin'
    - name: Build with Maven
      run: mvn -B clean install verify
```

**Changes:**
- `actions/checkout@v1` → `actions/checkout@v4` (current version)
- `actions/setup-java@v1` → `actions/setup-java@v4` (current version)
- `java-version: 1.8` → `java-version: 11`
- Added `distribution: 'temurin'` (modern best practice for Java distros)

- [ ] **Step 2: Commit**

```bash
git add .github/workflows/maven.yml
git commit -m "chore: update GitHub Actions to JDK 11 and latest action versions (checkout@v4, setup-java@v4)"
```

---

## Phase 5: Summary & Verification

### Task 12: Final verification and summary

**Files:**
- Reference: pom.xml, .github/workflows/maven.yml

**Interfaces:**
- Consumes: all updates from Tasks 2-11
- Produces: documented summary of changes, confirmed working build

- [ ] **Step 1: Create summary of all dependency changes**

```
DEPENDENCY UPDATE SUMMARY:

CORE FRAMEWORK:
✓ Spring Framework: 5.1.9.RELEASE → 5.3.33 (beans, core, test)
✓ Lombok: 1.18.10 → 1.18.30

COMMONS & UTILITIES:
✓ commons-lang: 2.6 (deprecated) → commons-lang3 3.14.0
✓ commons-collections: 20040616 (2004) → 3.2.2
✓ commons-io: 2.6 → 2.15.1
✓ Guava: 28.1-jre → 32.1.3-jre

LOGGING (FIXED):
✓ SLF4J API: 2.0.0-alpha0 (alpha) → 1.7.36 (stable)
✓ SLF4J log4j12: 2.0.0-alpha0 (alpha) → 1.7.36 (stable)
✓ log4j: 1.2.17 (unchanged - EOL but stable)

TESTING:
✓ JUnit: 4.13-beta-3 (beta) → 4.13.2 (stable)

BUILD PLUGINS:
✓ maven-compiler-plugin: 3.1 → 3.11.0
✓ jacoco-maven-plugin: 0.7.1.201405082137 → 0.8.10
✓ plantuml-maven-plugin: 1.1 → 1.3.3
✓ plantuml: 7999 → 1.2023.13

REPORTING PLUGINS:
✓ maven-project-info-reports-plugin: 2.9 → 3.5.0
✓ versions-maven-plugin: 1.2 → 2.16.2
✓ maven-dependency-plugin: 2.4 → 3.6.1
✓ maven-javadoc-plugin: 2.10.4 → 3.6.3
✓ umldoclet: 1.0.9 → 2.4.8

JAVA:
✓ Source/Target: 1.8 → 11

CI/CD:
✓ GitHub Actions checkout: v1 → v4
✓ GitHub Actions setup-java: v1 → v4
✓ GitHub Actions JDK: 1.8 → 11
✓ GitHub Actions Java distribution: added temurin
```

- [ ] **Step 2: Run final full build**

```bash
mvn clean verify
```

Expected: BUILD SUCCESS, all tests PASS

- [ ] **Step 3: Verify GitHub Actions will work**

Push a commit to trigger workflow:
```bash
git push origin master
```

Wait ~2 minutes, then check GitHub Actions tab in web browser for successful build.

- [ ] **Step 4: Final commit documenting completion**

```bash
git log --oneline | head -10
```

Verify you see commits from Tasks 2-11. Summary done.
