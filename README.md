# RAG KYC Compliance Policy Q&A System

## Overview
AI-powered Banking Compliance Policy Q&A System
built with Java Spring Boot and Spring AI using
RAG (Retrieval Augmented Generation) architecture.

Enables bank employees and compliance officers to
query banking policies in plain English and get
accurate, policy-specific answers with rule references.

---

## Problem Statement
Banks have thousands of compliance policy documents.
Manual search is:
- Time consuming (hours per query)
- Inconsistent answers
- No audit trail
- Human error prone

## Solution
RAG-powered chatbot that:
- Loads all bank compliance policies
- Employees ask questions in plain English
- Semantically searches relevant policies
- AI provides policy-specific answers
- With exact rule/policy references

---

## How RAG Works
```
Step 1 → LOAD
          Compliance policies →
          Converted to vectors (embeddings) →
          Stored in Vector Store

Step 2 → SEARCH
          Employee question →
          Converted to vector →
          Similar policies found (semantic search)

Step 3 → GENERATE
          Relevant policies + Question →
          AI generates specific answer →
          With policy reference
```

---

## Why RAG over Normal AI
```
Normal AI:
"What to do with SSN?"
→ Generic answer
→ No policy reference
→ Not auditable

RAG:
"What to do with SSN?"
→ POLICY-KYC-005 retrieved
→ "Encrypt using AES-256 before storing"
→ Specific + Auditable + Traceable
```

---

## Compliance Policies Loaded
```
KYC Policies:
POLICY-KYC-001 → Government ID Requirements
POLICY-KYC-002 → Address Proof Requirements
POLICY-KYC-003 → Income Verification
POLICY-KYC-004 → Joint Account Requirements
POLICY-KYC-005 → SSN Handling Policy
POLICY-KYC-006 → NRI Account Requirements

AML Policies:
POLICY-AML-001 → Cash Transaction Reporting
POLICY-AML-002 → Suspicious Activity Reporting
POLICY-AML-003 → High Risk Customer Due Diligence

Security Policies:
POLICY-PCI-001 → Card Data Security
POLICY-GDPR-001 → Data Privacy Requirements
POLICY-FRAUD-001 → Identity Fraud Prevention
```

---

## API Endpoints
```
POST /api/rag/compliance/query
Port: 8081
Content-Type: text/plain
Body: Employee question in plain English
```

---

## Sample Test Commands

### Windows CMD:

**Test 1 — SSN Storage Policy:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "How should we store customer SSN in our database?"
```

Expected:
```
POLICY_REFERENCE: POLICY-KYC-005
ANSWER: SSN must be encrypted using AES-256
        before database storage. Never store
        in plain text.
ACTION_REQUIRED: Implement AES-256 encryption
ESCALATE_TO: Security team if not implemented
```

---

**Test 2 — Joint Account Documents:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "What documents do we need for joint account opening?"
```

Expected:
```
POLICY_REFERENCE: POLICY-KYC-004
ANSWER: Government ID from all applicants,
        Each SSN verified separately,
        Combined income proof required
ACTION_REQUIRED: Collect docs from all applicants
```

---

**Test 3 — AML Suspicious Activity:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "Customer deposited $9,800 three times this month. What should I do?"
```

Expected:
```
POLICY_REFERENCE: POLICY-AML-001, POLICY-AML-002
ANSWER: This is structuring — illegal activity
        designed to avoid $10,000 threshold.
        SAR must be filed within 30 days.
ACTION_REQUIRED: File SAR immediately
ESCALATE_TO: AML Compliance team
```

---

**Test 4 — NRI Account:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "What documents are required for NRI account opening?"
```

Expected:
```
POLICY_REFERENCE: POLICY-KYC-006
ANSWER: Valid Passport with visa,
        Overseas address proof,
        Indian PAN card,
        Foreign bank statement 6 months,
        Source of funds declaration
ACTION_REQUIRED: Enhanced due diligence required
```

---

**Test 5 — Card Data:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "Can we store customer CVV number in our system?"
```

Expected:
```
POLICY_REFERENCE: POLICY-PCI-001
ANSWER: NO. CVV must never be stored
        after transaction authorization.
        PCI-DSS strictly prohibits this.
ACTION_REQUIRED: Remove any stored CVVs immediately
ESCALATE_TO: Security and Compliance team
```

---

**Test 6 — Expired Document:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "Can we accept expired passport for KYC verification?"
```

Expected:
```
POLICY_REFERENCE: POLICY-KYC-001
ANSWER: No. Documents expired more than
        30 days are rejected for KYC.
ACTION_REQUIRED: Request valid unexpired document
```

---

**Test 7 — Data Breach:**
```cmd
curl -X POST http://localhost:8081/api/rag/compliance/query -H "Content-Type: text/plain" -d "We discovered a data breach today. What are our obligations?"
```

Expected:
```
POLICY_REFERENCE: POLICY-GDPR-001
ANSWER: Data breach must be reported
        within 72 hours.
ACTION_REQUIRED: Notify regulators within 72 hours
ESCALATE_TO: Legal and Compliance team immediately
```

---

### PowerShell:

**Test 1 — SSN Policy:**
```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8081/api/rag/compliance/query" `
  -ContentType "text/plain" `
  -Body "How should we store customer SSN in our database?"
```

**Test 2 — Joint Account:**
```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8081/api/rag/compliance/query" `
  -ContentType "text/plain" `
  -Body "What documents do we need for joint account opening?"
```

**Test 3 — AML Structuring:**
```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8081/api/rag/compliance/query" `
  -ContentType "text/plain" `
  -Body "Customer deposited 9800 three times this month. What should I do?"
```

**Test 4 — NRI Account:**
```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8081/api/rag/compliance/query" `
  -ContentType "text/plain" `
  -Body "What documents are required for NRI account opening?"
```

**Test 5 — CVV Storage:**
```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8081/api/rag/compliance/query" `
  -ContentType "text/plain" `
  -Body "Can we store customer CVV number in our system?"
```

---

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Core language |
| Spring Boot | 3.3.5 | Framework |
| Spring AI | 1.0.0-M3 | AI + RAG Integration |
| OpenAI API | text-embedding-ada-002 | Embeddings |
| OpenAI API | GPT-3.5-turbo | Response Generation |
| SimpleVectorStore | In-Memory | Vector Storage |
| Maven | Latest | Build tool |

---

## Architecture
```
Employee Question
      ↓
Spring Boot REST API
      ↓
Spring AI Embedding
(Question → Vector)
      ↓
SimpleVectorStore
Similarity Search
      ↓
Top 3 Relevant Policies Retrieved
      ↓
OpenAI GPT-3.5
(Question + Policies → Answer)
      ↓
Policy Referenced Response
      ↓
Employee Gets Specific Answer
```

---

## Key Features
- Semantic search — not keyword matching
- Policy specific answers with references
- Auditable responses for compliance
- Natural language queries
- No manual document search needed
- Consistent answers every time

---

## Future Enhancements
- PDF policy document ingestion
- Persistent vector store (Pinecone/pgvector)
- Conversation history
- Multi-language support
- Azure deployment
- Admin portal for policy updates
- Audit trail database

---

## Related Project
KYC Document Verification System:
github.com/NallaChaitanya/kyc-document-verifier

---

## Author
**Chaitanya Nalla**
Senior Full Stack Java Engineer | GenAI | Fintech
UBS Financial Services
- LinkedIn: linkedin.com/in/chaitanya-nalla
- GitHub: github.com/NallaChaitanya