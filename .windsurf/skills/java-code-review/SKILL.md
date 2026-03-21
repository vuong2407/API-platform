---
name: java-code-review
description: Review Java code for quality and best practices.
---

# Code Review Checklist

Check the following:

Architecture

- controller contains no business logic
- service layer exists
- repository only accesses database

Code quality

- method names meaningful
- no duplicated code
- no large classes
- null checks where appropriate

Error handling

- custom exceptions
- meaningful messages

Testing

- unit tests exist
- good test coverage