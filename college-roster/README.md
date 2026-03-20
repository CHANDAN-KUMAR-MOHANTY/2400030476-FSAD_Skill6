# FSAD Skill-10 - College Roster (React useState)
**Course:** 24SDCS02 / 24SDCS02E / 24SDCS02P / 24SDCS02L
**Topic:** React Hooks - useState with Object Array (Enrol / Display / Remove)

---

## Project Structure

```
college-roster/
├── package.json
├── public/
│   └── index.html
└── src/
    ├── index.js          <- Entry point
    ├── styles.css        <- Global CSS variables and reset
    └── RosterBoard.js    <- Main component (all tasks)
```

---

## How to Run

```bash
cd college-roster
npm install
npm start
# Opens at http://localhost:3000
```

---

## Features

| Task | What is implemented |
|---|---|
| Task 1 | RosterBoard component |
| Task 2 | 5 default students with rollNo, fullName, branch |
| Task 3 | useState for roster array + formData object |
| Task 4 | Controlled inputs via onFieldChange handler |
| Task 5 | Enrol button adds to roster, clears form |
| Task 6 | roster.map() renders each row with key prop |
| Task 7 | Remove button filters student out of state |
| Task 8 | "No students available" message when empty |
| Task 9 | Light warm theme, branch color chips, panel layout |

---

## Push to GitHub

```bash
git init
git add .
git commit -m "FSAD Skill-10: React useState College Roster"
git remote add origin https://github.com/<your-username>/college-roster.git
git branch -M main
git push -u origin main
```

---

## Viva Questions and Answers

**Q1. What is the purpose of the useState hook in React?**
useState allows a functional component to hold and update its own local state between renders. It returns the current state value and a setter function. Calling the setter causes React to re-render the component with the new value, keeping the UI in sync with the data automatically.

**Q2. How does React update the UI when state changes?**
When setRoster or setFormData is called, React schedules a re-render. The component function runs again with the updated state, producing a new virtual DOM. React then diffs the old and new virtual DOM trees and applies only the minimal changes to the real DOM, making updates fast and efficient.

**Q3. Why do we store form inputs in state for controlled components?**
Storing input values in state (via formData) makes React the single source of truth for what the user typed. This lets us read, validate, and clear the form values programmatically. Without state, we would have to reach into the DOM directly using refs, which is harder to manage and test.

**Q4. What is the importance of using keys when rendering lists?**
The key prop (set to entry.rollNo here) lets React uniquely identify each list item. When the roster changes, React uses keys to decide which DOM nodes to add, remove, or reuse. Without keys, React falls back to index-based comparison, which can produce incorrect renders when items are inserted or deleted in the middle of the list.

**Q5. Can one component use multiple useState variables? Why?**
Yes. RosterBoard uses four: roster for the student list, formData for the input fields, validationMsg for error feedback, and highlighted for the new-row animation. Each useState call is completely independent. Using separate variables is cleaner than one big object because each setter only updates its own slice of state, and the code reads clearly.
