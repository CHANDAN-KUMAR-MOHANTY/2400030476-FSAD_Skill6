import { useState } from "react";

// Default roster loaded when the app first starts
const DEFAULT_ROSTER = [
  { rollNo: 101, fullName: "Meera Krishnan",   branch: "Computer Science"        },
  { rollNo: 102, fullName: "Aditya Menon",      branch: "Artificial Intelligence" },
  { rollNo: 103, fullName: "Tanvi Joshi",       branch: "Data Science"            },
  { rollNo: 104, fullName: "Suresh Pillai",     branch: "Information Technology"  },
  { rollNo: 105, fullName: "Lakshmi Iyer",      branch: "Cyber Security"          },
];

// Blank entry used to reset the form after adding
const BLANK_ENTRY = { rollNo: "", fullName: "", branch: "" };

// Pick a highlight colour per branch
function pickColor(branch) {
  const map = {
    "Computer Science":        { text: "#c84b31", bg: "#fff0ed", border: "#f5c5bb" },
    "Artificial Intelligence": { text: "#2563a8", bg: "#eef4fd", border: "#bcd4f5" },
    "Data Science":            { text: "#2d7a4f", bg: "#edf7f1", border: "#a8dfc0" },
    "Information Technology":  { text: "#b07d1a", bg: "#fdf5e0", border: "#f0d89a" },
    "Cyber Security":          { text: "#6b3fa0", bg: "#f4eeff", border: "#cdb8f0" },
  };
  return map[branch] || { text: "#5a5a72", bg: "#f0f0f5", border: "#d4d4e0" };
}

export default function RosterBoard() {

  // ── State declarations ────────────────────────────────────────────────────
  // Holds the full list of enrolled students
  const [roster, setRoster]           = useState(DEFAULT_ROSTER);

  // Holds whatever the user is currently typing in the form
  const [formData, setFormData]       = useState(BLANK_ENTRY);

  // Stores a validation message when the form has missing/duplicate fields
  const [validationMsg, setValidation] = useState("");

  // Tracks the most recently enrolled roll number for a brief highlight
  const [highlighted, setHighlighted] = useState(null);

  // ── Handle input changes (controlled component) ───────────────────────────
  const onFieldChange = (evt) => {
    const { name, value } = evt.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    setValidation("");
  };

  // ── Enroll a new student ──────────────────────────────────────────────────
  const enrollStudent = () => {
    const roll   = formData.rollNo.toString().trim();
    const name   = formData.fullName.trim();
    const branch = formData.branch.trim();

    // Validate all fields present
    if (!roll || !name || !branch) {
      setValidation("Please fill in all three fields before enrolling.");
      return;
    }

    // Check for duplicate roll number
    const duplicate = roster.find(r => String(r.rollNo) === roll);
    if (duplicate) {
      setValidation(`Roll No. ${roll} is already in the roster.`);
      return;
    }

    const newEntry = { rollNo: Number(roll), fullName: name, branch };

    // Add to roster and clear the form
    setRoster(prev => [...prev, newEntry]);
    setFormData(BLANK_ENTRY);

    // Briefly highlight the new row
    setHighlighted(newEntry.rollNo);
    setTimeout(() => setHighlighted(null), 1800);
  };

  // ── Remove a student from the roster ─────────────────────────────────────
  const removeStudent = (rollNo) => {
    setRoster(prev => prev.filter(entry => entry.rollNo !== rollNo));
  };

  // ── Render ────────────────────────────────────────────────────────────────
  return (
    <div style={s.page}>

      {/* ── Masthead ──────────────────────────────────────────────────── */}
      <div style={s.masthead}>
        <p style={s.courseLabel}>FSAD · Skill 10 · React useState</p>
        <h1 style={s.heading}>College<br/>Roster</h1>
        <p style={s.byline}>
          Manage enrolled students using React's&nbsp;
          <span style={s.pill}>useState</span> hook
        </p>

        <div style={s.counters}>
          <div style={s.counter}>
            <span style={s.counterVal}>{roster.length}</span>
            <span style={s.counterKey}>Enrolled</span>
          </div>
          <div style={s.divider} />
          <div style={s.counter}>
            <span style={s.counterVal}>
              {new Set(roster.map(r => r.branch)).size}
            </span>
            <span style={s.counterKey}>Branches</span>
          </div>
        </div>
      </div>

      {/* ── Enrolment Form ────────────────────────────────────────────── */}
      <div style={s.panel}>
        <h2 style={s.panelTitle}>Enrol a Student</h2>

        <div style={s.row}>
          <Field
            label="Roll No."
            name="rollNo"
            type="number"
            placeholder="e.g. 106"
            value={formData.rollNo}
            onChange={onFieldChange}
          />
          <Field
            label="Full Name"
            name="fullName"
            type="text"
            placeholder="e.g. Neha Varma"
            value={formData.fullName}
            onChange={onFieldChange}
            wide
          />
          <Field
            label="Branch"
            name="branch"
            type="text"
            placeholder="e.g. Data Science"
            value={formData.branch}
            onChange={onFieldChange}
            wide
          />
        </div>

        {validationMsg && (
          <p style={s.warning}>⚠ {validationMsg}</p>
        )}

        <button
          style={s.enrollBtn}
          onClick={enrollStudent}
          onMouseEnter={e => e.currentTarget.style.background = "#a83a23"}
          onMouseLeave={e => e.currentTarget.style.background = "#c84b31"}
        >
          Enrol Student →
        </button>
      </div>

      {/* ── Roster Table ──────────────────────────────────────────────── */}
      <div style={s.panel}>
        <div style={s.tableHeader}>
          <h2 style={s.panelTitle}>Enrolled Students</h2>
          <span style={s.count}>{roster.length} record{roster.length !== 1 ? "s" : ""}</span>
        </div>

        {roster.length === 0 ? (
          // Task 8 – empty state
          <div style={s.emptyBox}>
            <div style={s.emptyIcon}>🗂️</div>
            <p style={s.emptyTitle}>No students available</p>
            <p style={s.emptyNote}>Use the form above to enrol your first student.</p>
          </div>
        ) : (
          <table style={s.table}>
            <thead>
              <tr>
                {["Roll No.", "Full Name", "Branch", "Action"].map(col => (
                  <th key={col} style={s.th}>{col}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {roster.map(entry => {
                const clr = pickColor(entry.branch);
                const isNew = highlighted === entry.rollNo;
                return (
                  // key prop – unique identifier for React reconciliation
                  <tr
                    key={entry.rollNo}
                    style={{
                      ...s.trow,
                      background: isNew ? "#fff8f0" : "#fff",
                      transition: "background 0.6s",
                    }}
                  >
                    <td style={s.tdRoll}>{entry.rollNo}</td>
                    <td style={s.td}>{entry.fullName}</td>
                    <td style={s.td}>
                      <span style={{
                        ...s.chip,
                        color:       clr.text,
                        background:  clr.bg,
                        borderColor: clr.border,
                      }}>
                        {entry.branch}
                      </span>
                    </td>
                    <td style={s.td}>
                      <button
                        style={s.removeBtn}
                        onClick={() => removeStudent(entry.rollNo)}
                        onMouseEnter={e => {
                          e.currentTarget.style.background = "#fff0ed";
                          e.currentTarget.style.color      = "#c84b31";
                          e.currentTarget.style.borderColor = "#c84b31";
                        }}
                        onMouseLeave={e => {
                          e.currentTarget.style.background  = "transparent";
                          e.currentTarget.style.color       = "#5a5a72";
                          e.currentTarget.style.borderColor = "#e4ddd4";
                        }}
                      >
                        Remove
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </div>

      <p style={s.footer}>
        College Roster App &nbsp;·&nbsp; FSAD Skill-10 &nbsp;·&nbsp; 24SDCS02
      </p>
    </div>
  );
}

// ── Reusable Field sub-component ─────────────────────────────────────────────
function Field({ label, name, type, placeholder, value, onChange, wide }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "6px", flex: wide ? 2 : 1 }}>
      <label style={s.fieldLabel}>{label}</label>
      <input
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        style={s.input}
        onFocus={e  => (e.target.style.borderColor = "#c84b31")}
        onBlur={e   => (e.target.style.borderColor = "#e4ddd4")}
      />
    </div>
  );
}

// ── Styles ────────────────────────────────────────────────────────────────────
const s = {
  page: {
    maxWidth:     "880px",
    margin:       "0 auto",
    padding:      "40px 24px 80px",
    display:      "flex",
    flexDirection:"column",
    gap:          "24px",
  },
  masthead: {
    padding:      "32px 0 16px",
  },
  courseLabel: {
    fontSize:     "12px",
    letterSpacing:"0.1em",
    textTransform:"uppercase",
    color:        "#c84b31",
    marginBottom: "14px",
    fontWeight:   500,
  },
  heading: {
    fontFamily:   "'Playfair Display', serif",
    fontSize:     "clamp(52px, 9vw, 88px)",
    fontWeight:   900,
    lineHeight:   1.0,
    color:        "#1a1a2e",
    marginBottom: "16px",
    letterSpacing:"-0.02em",
  },
  byline: {
    fontSize:     "15px",
    color:        "#5a5a72",
    marginBottom: "28px",
    lineHeight:   1.6,
  },
  pill: {
    background:   "#fff0ed",
    color:        "#c84b31",
    border:       "1px solid #f5c5bb",
    borderRadius: "4px",
    padding:      "1px 8px",
    fontSize:     "13px",
    fontFamily:   "'IBM Plex Sans', monospace",
  },
  counters: {
    display:      "flex",
    alignItems:   "center",
    gap:          "20px",
  },
  counter: {
    display:      "flex",
    flexDirection:"column",
    gap:          "2px",
  },
  counterVal: {
    fontFamily:   "'Playfair Display', serif",
    fontSize:     "36px",
    fontWeight:   700,
    color:        "#c84b31",
    lineHeight:   1,
  },
  counterKey: {
    fontSize:     "11px",
    color:        "#5a5a72",
    textTransform:"uppercase",
    letterSpacing:"0.08em",
  },
  divider: {
    width:        "1px",
    height:       "40px",
    background:   "#e4ddd4",
  },
  panel: {
    background:   "#ffffff",
    border:       "1px solid #e4ddd4",
    borderRadius: "12px",
    padding:      "28px 32px",
    boxShadow:    "0 2px 16px rgba(0,0,0,0.05)",
  },
  panelTitle: {
    fontFamily:   "'Playfair Display', serif",
    fontSize:     "20px",
    fontWeight:   700,
    color:        "#1a1a2e",
    marginBottom: "20px",
  },
  row: {
    display:      "flex",
    gap:          "16px",
    marginBottom: "18px",
    flexWrap:     "wrap",
  },
  fieldLabel: {
    fontSize:     "11px",
    textTransform:"uppercase",
    letterSpacing:"0.08em",
    color:        "#5a5a72",
    fontWeight:   500,
  },
  input: {
    border:       "1px solid #e4ddd4",
    borderRadius: "8px",
    padding:      "10px 14px",
    fontSize:     "14px",
    fontFamily:   "'IBM Plex Sans', sans-serif",
    color:        "#1a1a2e",
    outline:      "none",
    background:   "#faf7f2",
    transition:   "border-color 0.2s",
    width:        "100%",
  },
  warning: {
    color:        "#c84b31",
    fontSize:     "13px",
    background:   "#fff0ed",
    border:       "1px solid #f5c5bb",
    borderRadius: "6px",
    padding:      "10px 14px",
    marginBottom: "16px",
  },
  enrollBtn: {
    background:   "#c84b31",
    color:        "#fff",
    border:       "none",
    borderRadius: "8px",
    padding:      "11px 32px",
    fontSize:     "14px",
    fontFamily:   "'IBM Plex Sans', sans-serif",
    fontWeight:   600,
    cursor:       "pointer",
    transition:   "background 0.2s",
    letterSpacing:"0.03em",
  },
  tableHeader: {
    display:      "flex",
    alignItems:   "center",
    justifyContent:"space-between",
    marginBottom: "0",
  },
  count: {
    fontSize:     "12px",
    color:        "#5a5a72",
    background:   "#f0ece6",
    padding:      "3px 10px",
    borderRadius: "20px",
  },
  table: {
    width:        "100%",
    borderCollapse:"collapse",
    marginTop:    "4px",
  },
  th: {
    padding:      "10px 16px",
    textAlign:    "left",
    fontSize:     "11px",
    textTransform:"uppercase",
    letterSpacing:"0.08em",
    color:        "#5a5a72",
    borderBottom: "2px solid #e4ddd4",
    fontWeight:   600,
  },
  trow: {
    borderBottom: "1px solid #f0ece6",
  },
  tdRoll: {
    padding:      "14px 16px",
    fontSize:     "13px",
    fontFamily:   "'IBM Plex Sans', monospace",
    color:        "#5a5a72",
    fontWeight:   500,
  },
  td: {
    padding:      "14px 16px",
    fontSize:     "14px",
    color:        "#1a1a2e",
  },
  chip: {
    display:      "inline-block",
    padding:      "3px 12px",
    borderRadius: "20px",
    fontSize:     "12px",
    fontWeight:   500,
    border:       "1px solid",
    whiteSpace:   "nowrap",
  },
  removeBtn: {
    background:   "transparent",
    color:        "#5a5a72",
    border:       "1px solid #e4ddd4",
    borderRadius: "6px",
    padding:      "5px 14px",
    fontSize:     "12px",
    cursor:       "pointer",
    fontFamily:   "'IBM Plex Sans', sans-serif",
    transition:   "all 0.2s",
  },
  emptyBox: {
    textAlign:    "center",
    padding:      "56px 20px",
  },
  emptyIcon: {
    fontSize:     "48px",
    marginBottom: "12px",
  },
  emptyTitle: {
    fontFamily:   "'Playfair Display', serif",
    fontSize:     "22px",
    fontWeight:   700,
    color:        "#1a1a2e",
    marginBottom: "8px",
  },
  emptyNote: {
    fontSize:     "14px",
    color:        "#5a5a72",
  },
  footer: {
    textAlign:    "center",
    fontSize:     "12px",
    color:        "#b0a898",
    marginTop:    "8px",
    letterSpacing:"0.06em",
  },
};
