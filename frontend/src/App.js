// 필요한 도구(React 기능)를 가져옴
// useState: 데이터를 담을 바구니
// useEffect: 화면이 뜨자마자 실행할 행동
import React, { useEffect, useState } from 'react';

function App() {
    // 'summary'라는 이름의 데이터 바구니 생성
    // {}는 비어있는 객체라는 뜻
    // setSummary는 이 바구니에 데이터를 넣을 때 쓰는 전용 집게
    const [summary, setSummary] = useState({});
    const [transactions, setTransactions] = useState([]); // 내역 리스트 담을 바구니(배열)
    const [description, setDescription] = useState("");
    const [amount, setAmount] = useState(0);
    const [category, setCategory] = useState("식비"); // 기본값

    // 데이터를 새로고침하는 함수
    const fetchData = () => {
        fetch('http://localhost:8080/summary').then(res => res.json()).then(setSummary);
        fetch('http://localhost:8080/list').then(res => res.json()).then(setTransactions);
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleSave = () => {
        const newTransaction = {
            type: "EXPENDITURE",
            description: description, // desc 대신 description
            amount: parseInt(amount),  // money 객체 없이 바로 amount
            category: category,
        };

        fetch('http://localhost:8080/record', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newTransaction) // 자바스크립트 객체를 JSON 문자열로 변환!
        })
        .then(res => {
            if (res.ok) {
                alert("저장 성공!");
                fetchData();
                setDescription("");
                setAmount(0);
            } else {
                // 여기서 에러 메시지를 확인하면 디버깅이 쉬워요
                res.text().then(text => alert("저장 실패: " + text));
            }
        })
        .catch(err => console.error("네트워크 에러: ", err));
    }

    // 화면에 실제로 보여줄 HTML(JSX)을 작성
    return (
        <div style={{ padding: '20px' }}>
            <h1>📊 나의 가계부</h1>
            {/* --- 입력 섹션 --- */}
            <section style={{ marginBottom: '30px', padding: '15px', border: '1px solid #ccc' }}>
                <h3>새 지출 기록하기</h3>
                <input
                    type="text" placeholder="내용"
                    value={description} onChange={(e) => setDescription(e.target.value)}
                />
                <input
                    type="number" placeholder="금액"
                    value={amount} onChange={(e) => setAmount(e.target.value)}
                />
                <select value={category} onChange={(e) => setCategory(e.target.value)}>
                    <option value="식비">식비</option>
                    <option value="교통">교통</option>
                    <option value="문화">문화</option>
                    <option value="기타">기타</option>
                </select>
                <button onClick={handleSave}>저장하기</button>
            </section>
            {/* 상단 통계 섹션 */}
            <section>
                <h3>카테고리별 합계</h3>
                {Object.entries(summary).map(([cat, amt]) => (
                    <span key={cat} style={{ marginRight: '10px', background: '#eee', padding: '5px' }}>
            {cat}: {amt.toLocaleString()}원
          </span>
                ))}
            </section>

            <hr />

            {/* 하단 리스트 섹션 */}
            <section>
                <h3>상세 내역</h3>
                <table border="1" style={{ width: '100%', textAlign: 'left', borderCollapse: 'collapse' }}>
                    <thead>
                    <tr>
                        <th>날짜</th>
                        <th>내용</th>
                        <th>카테고리</th>
                        <th>금액</th>
                    </tr>
                    </thead>
                    <tbody>
                    {/* 3. 자바의 Stream처럼 리스트를 순회하며 <tr> 태그를 생성 */}
                    {transactions.map((t) => (
                        <tr key={t.id}>
                            <td>{new Date(t.timestamp).toLocaleDateString()}</td>
                            <td>{t.description}</td>
                            <td>{t.category}</td>
                            {/* 여기서도 toLocaleString()으로 콤마 찍기! */}
                            <td>{t.money.amount.toLocaleString()}원</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </section>
        </div>
    );
}

export default App;