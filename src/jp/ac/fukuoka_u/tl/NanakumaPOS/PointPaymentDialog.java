package jp.ac.fukuoka_u.tl.NanakumaPOS;

//-*- java -*-
/******************************************************************************
*
*  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
*
*  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
*
*  「ポイント決済」ダイアログ。
*
*****************************************************************************/

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PointPaymentDialog extends JDialog implements ActionListener{
	/* データ */
	// 合計金額
	private int totalPrice;
	// お預かり
	private int paidPoint;
	// OKボタンが押されたか
	private Boolean confirmed;

	/* ウィジェット */
	private JFrame owner;
	// 合計金額欄ラベル
	private JLabel totalPriceLabel;
	// 合計金額欄
	private JTextField totalPriceField;
	// ポイント欄ラベル
	private JLabel paidPointLabel;
	// ポイント欄
	private JTextField paidPointField;
	// 決済ボタン
	private JButton okButton;
	// 中止ボタン
	private JButton cancelButton;

	/*
	 * コンストラクタ
	 */
	public PointPaymentDialog(JFrame _owner, int _totalPrice) {
		super(_owner, true);
		owner = _owner;
		totalPrice = _totalPrice;
		confirmed = false;

		setLayout(null);
		setTitle("ポイント決済");
		setSize(248, 175);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();

		// 合計金額欄を生成する。
		totalPriceLabel = new JLabel("合計金額");
		totalPriceLabel.setBounds(16, 16, 100, 24);
		contentPane.add(totalPriceLabel);
		totalPriceField = new JTextField (8);
		totalPriceField.setBounds(116, 16, 100, 24);
		totalPriceField.setBackground(Color.YELLOW);
		totalPriceField.setHorizontalAlignment(JTextField.RIGHT);
		totalPriceField.setEditable(false);
		totalPriceField.setFocusable(false);
		totalPriceField.setText(Integer.toString(totalPrice));
		contentPane.add(totalPriceField);

		// ポイント欄を生成する。
		paidPointLabel = new JLabel("使用ポイント");
		paidPointLabel.setBounds(16, 48, 100, 24);
		contentPane.add(paidPointLabel);
		paidPointField = new JTextField (8);
		paidPointField.setBounds(116, 48, 100, 24);
		paidPointField.setBackground(Color.YELLOW);
		paidPointField.setHorizontalAlignment(JTextField.RIGHT);
		paidPointField.setEditable(true);
		contentPane.add(paidPointField);

		// OKボタンを生成する。
		okButton = new JButton("ポイント決済");
		okButton.setBounds(26, 96, 80, 24);
		okButton.addActionListener(this);
		okButton.setActionCommand("ok");
		contentPane.add(okButton);

		// キャンセルボタンを生成する。
		cancelButton = new JButton("中止");
		cancelButton.setBounds(126, 96, 80, 24);;
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		contentPane.add(cancelButton);
	}

	/*
	 * 決済ダイアログを閉じるときにOKボタンが押されたかを返す。
	 */
	public Boolean isConfirmed() {
		return confirmed;
	}

	/*
	 * ポイントを返す。
	 */
	public int getPaidPoint() {
		return paidPoint;
	}

	/*
	 * 決済の意思が確認されたときに呼び出される。
	 */
	private void paymentConfirmed() {
		try {
			paidPoint = Integer.parseInt(paidPointField.getText());
			int Point = Integer.parseInt(CheckArticlesScreenPanel.pointField.getText());
			
			if	(paidPoint > Point) {
			JOptionPane.showMessageDialog(owner, "ポイントの入力が不正です。", "エラー", JOptionPane.ERROR_MESSAGE);
			paidPointField.requestFocusInWindow();
			return;
			}
			if (paidPoint > totalPrice) {
				JOptionPane.showMessageDialog(owner, "合計金額を超えています。", "エラー", JOptionPane.ERROR_MESSAGE);
				paidPointField.requestFocusInWindow();
				return;
			}
		}
		catch (NumberFormatException ex) {
		
		}
		confirmed = true;
		dispose();
	}

	/*
	 * 決済の意思が中止されたときに呼び出される。
	 */
	private void paymentCancelled() {
		paidPoint = 0;
		confirmed = false;
		dispose();
	}

	/*
	 * ボタンが押されたときに呼び出される。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			paymentConfirmed();
		} else if (e.getActionCommand().equals("cancel")) {
			paymentCancelled();
		}
	}
}
