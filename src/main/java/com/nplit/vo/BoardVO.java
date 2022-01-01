package com.nplit.vo;

import java.sql.Date;
import lombok.Data;

@Data
public class BoardVO {
	private int seq; // �Խñ� ��ȣ
	private String title; // �Խñ� ����
	private String writer; // �ۼ���
	private String category; // ī�װ�
	private String tag; // �±�
	private int hit; // ��ȸ��
	private String answer; // �亯
	private String contents; // ����
	private String price; // �ݾ�
	private String productname; // ��ǰ��
	private String maxNum; // �ִ� �����ο�
	private String address; // ���
	private String productlink; // ��ǰ��ũ
	private String cover_img; // ��ǰ ��ǥ �̹���
	private String deleteYN; // �� ��������
	private Date regDate; // �� �����
	private Date updateDate; // �� ������
	private String file_no; // ���Ϲ�ȣ
	private String status; // �� �亯����
	private String totalNum; // �� �亯����
	private String favorite; // �� ���
	private String reported_id; // �Ű���
//   private String SearchCondition;
	private String SearchKeyword; // ��ǰ��
	private String SearchCategory; // ī�װ���
	private String SearchAddress; // ������
}