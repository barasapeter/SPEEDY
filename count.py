import os
import fnmatch


def count_lines_of_code(directory, extensions=None):
    if extensions is None:
        extensions = {
            "*.py": "Python",
            "*.js": "JavaScript",
            "*.java": "Java",
            "*.cpp": "C++",
            "*.c": "C",
            "*.cs": "C#",
            "*.rb": "Ruby",
            "*.go": "Go",
            "*.rs": "Rust",
            "*.php": "PHP",
            "*.html": "HTML",
            "*.css": "CSS",
            "*.swift": "Swift",
            "*.kt": "Kotlin",
            "*.ts": "TypeScript",
            "*.scala": "Scala",
            "*.sh": "Shell",
            "*.r": "R",
            "*.pl": "Perl",
            "*.m": "MATLAB",
            "*.sql": "SQL",
            "*.xml": "XML",
            "*.json": "JSON",
            "*.yaml": "YAML",
            "*.yml": "YAML",
            "*.jl": "Julia",
            "*.dart": "Dart",
            "*.hs": "Haskell",
            "*.erl": "Erlang",
            "*.ex": "Elixir",
            "*.exs": "Elixir",
            "*.cls": "Apex",
            "*.vb": "Visual Basic",
            "*.vbs": "VBScript",
            "*.jsp": "JavaServer Pages",
            "*.asp": "ASP",
            "*.aspx": "ASP.NET",
            "*.scss": "Sass",
            "*.sass": "Sass",
            "*.less": "LESS",
            "*.md": "Markdown",
        }

    total_lines = 0
    languages_found = set()

    for root, _, files in os.walk(directory):
        for extension, language in extensions.items():
            for filename in fnmatch.filter(files, extension):
                file_path = os.path.join(root, filename)
                languages_found.add(language)
                try:
                    with open(
                        file_path, "r", encoding="utf-8", errors="ignore"
                    ) as file:
                        lines = file.readlines()
                        total_lines += len(lines)
                except Exception as e:
                    print(f"Could not read file {file_path}: {e}")

    return total_lines, languages_found


directory_path = "."
total_lines, languages_found = count_lines_of_code(directory_path)
print(f"number of lines of code: {total_lines}")
print(f"Languages: {', '.join(sorted(languages_found))}")
