# Claude Code Preferences for sudoku-solver

## IDE MCP Usage

**Prefer IDEA MCP tools over CLI commands whenever possible.**

When working with this project:
- Use `mcp__idea__*` tools for code navigation, editing, building, and testing
- Use `mcp__idea__execute_terminal_command` for terminal operations only when necessary
- CLI tools (Bash) are fallback only — use them when IDEA MCP doesn't provide the needed capability

### Common IDEA MCP Tools to Use

- `mcp__idea__search_text` / `mcp__idea__search_regex` - Search code
- `mcp__idea__search_symbol` - Find symbols/classes/methods
- `mcp__idea__get_file_problems` - Get linting/compilation errors
- `mcp__idea__lint_files` - Run code inspection
- `mcp__idea__execute_run_configuration` - Run Maven build/test targets
- `mcp__idea__build_project` - Build the project
- `mcp__idea__get_project_dependencies` - Inspect dependencies
- `mcp__idea__reformat_file` - Format code
- `mcp__idea__rename_refactoring` - Refactor/rename safely

## Project Tech Stack

- **Language:** Java 21
- **Build:** Maven 3.x
- **Testing:** JUnit 4.13.2
- **Dependencies:** Spring 5.3.33, Apache Commons, SLF4J/Log4j, Guava
- **CI/CD:** GitHub Actions (JDK 21), Azure Pipelines (JDK 21)

## Development Guidelines

- All tests must pass before committing
- Use inline worktrees for isolated development
- Merge to master locally, then push
- Keep commits focused and well-documented
