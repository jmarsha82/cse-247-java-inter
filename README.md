# CSE 247 Java Interview Practice

This repository appears to be a college assignment or practice repository for Java algorithm work. The original files were a flat collection of source snippets for topics from CSE 247-style coursework, including Rabin-Karp rolling hashes, min-heaps, graph edges and vertices, and a time-aware shortest-path exercise.

The maintained, unit-tested portion of the repo is now the Rabin-Karp rolling hash implementation. The older assignment artifacts are preserved in `archive/assignment-originals/` so they can still be inspected without breaking the modern Maven build.

## What This Repo Does

The active project implements a Rabin-Karp rolling hash:

- `src/main/java/rabinkarp/RollingHasher.java` defines the small Java interface for stateful rolling hash implementations.
- `src/main/java/rabinkarp/RK.java` implements the original assignment's rolling hash behavior with base `31` and modulus `511`.
- `src/main/java/rabinkarp/RabinKarpCli.java` provides a command-line interface for printing the rolling hash value produced after each character in a target string.
- `src/test/java/rabinkarp/` contains JUnit 5 tests for the course example, randomized matching behavior, invalid input handling, interface behavior, and CLI output.

The archived school files include incomplete or course-dependent material, such as heap code that references missing support classes and graph/shortest-path code with package mismatches. Those files are intentionally not part of the Maven build.

## Project Layout

```text
.
├── archive/assignment-originals/   # Original classroom/practice source files
├── config/checkstyle.xml           # Java quality rules used by CI
├── src/main/java/rabinkarp/         # Maintained Java source
├── src/test/java/rabinkarp/         # Unit tests
├── .github/workflows/ci.yml        # GitHub Actions pipeline
├── .github/dependabot.yml          # Dependency update automation
└── pom.xml                         # Maven build, tests, coverage, and quality config
```

## Requirements

- Java 17 or newer
- Maven 3.9 or newer

The local machine used for this update did not have Java or Maven installed, but the GitHub Actions pipeline provisions Java automatically with `actions/setup-java`.

## Running Tests

Run the full verification command:

```bash
mvn verify
```

That command runs:

- JUnit 5 unit tests
- JaCoCo coverage reporting
- A JaCoCo line coverage gate requiring at least 90%
- Checkstyle static quality checks

To run only the unit tests:

```bash
mvn test
```

After `mvn verify`, the HTML coverage report is generated at:

```text
target/site/jacoco/index.html
```

## Running the CLI

Build the project:

```bash
mvn package
```

Then run the CLI with a window size and target string:

```bash
java -cp target/classes rabinkarp.RabinKarpCli 4 aabaacaadaabaabaa
```

Example output shape:

```text
0: a -> 97
1: a -> 38
2: b -> 254
3: a -> 306
```

## GitHub Actions Pipeline

The CI workflow runs on pull requests and pushes to `main`. It uses least-privilege default permissions, cancels stale workflow runs for the same branch, and caches Maven dependencies.

### Unit Tests

The `Unit Tests` job:

- Checks out the repository
- Installs Temurin Java 17
- Runs `mvn --batch-mode verify`
- Uploads the JaCoCo HTML coverage report as a workflow artifact

### Code Scanning: Quality

The `Code Scanning / Quality` job runs Checkstyle against main and test source code:

```bash
mvn --batch-mode checkstyle:check
```

This is a free, repo-local quality gate. It checks for basic Java hygiene issues such as wildcard imports, unused imports, and multiple top-level classes in one source file.

### Code Scanning: Security

The `Code Scanning / Security` job uses GitHub CodeQL for Java/Kotlin analysis:

- Initializes CodeQL
- Builds the Maven project with tests skipped
- Uploads semantic security findings to GitHub Code Scanning

CodeQL is GitHub-native. It is generally available for public repositories, while private repository availability can depend on the GitHub plan and Advanced Security settings.

The workflow also includes `Code Scanning / Security / Dependency Review` for pull requests. It uses GitHub's dependency review action to flag vulnerable or problematic dependency changes before they land.

### Dependency Automation

Dependabot is configured for:

- Maven dependencies in `pom.xml`
- GitHub Actions versions in workflow files

Dependabot opens weekly update pull requests, limited to five open PRs per ecosystem.

## Notes on the Original Assignment Files

The archived files are useful for understanding the original school context, but they were not all directly buildable:

- `TestRK.java` was an older JUnit 4 test for the Rabin-Karp implementation.
- `RK Updated.java` contained the cleaner Rabin-Karp implementation that became the maintained `RK` class.
- Heap files referenced missing course-provided types such as `PriorityQueue`, `Ticker`, `HeapToStrings`, and `MinHeapValidator`.
- Shortest-path files referenced package names that were not present in the repository.

Because the requirement was to ensure this repo has unit-tested code, the complete Rabin-Karp assignment was promoted into a conventional Maven project and covered with modern tests.
