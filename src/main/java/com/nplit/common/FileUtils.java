package com.nplit.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nplit.vo.AttachVO;

public class FileUtils {
	public List<AttachVO> parseFileInfo(int seq, HttpServletRequest request, MultipartHttpServletRequest mhsr)
			throws IOException {
		if (ObjectUtils.isEmpty(mhsr)) {
			return null;
		}

		List<AttachVO> fileList = new ArrayList<AttachVO>();

		// ������ ���� ��� ���
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "/upload/";

		// �� ����� ������ ������ ���� ����
		File file = new File(root_path + attach_path);
		if (file.exists() == false) {
			file.mkdir();
		}

		// ���� �̸����� iterator�� ����
		Iterator<String> iterator = mhsr.getFileNames();

		while (iterator.hasNext()) {
			// ���ϸ����� ���� ����Ʈ ��������
			List<MultipartFile> list = mhsr.getFiles(iterator.next());

			// ���� ����Ʈ ���� ��ŭ ������ ���� ����Ʈ�� ����ְ� ����
			for (MultipartFile mf : list) {
				if (mf.getSize() > 0) {
					AttachVO attach = new AttachVO();
					attach.setSeq(seq);
					attach.setFilesize(mf.getSize());
					attach.setFilename(mf.getOriginalFilename());
					attach.setUploadpath(root_path + attach_path);
					fileList.add(attach);

					file = new File(root_path + attach_path + mf.getOriginalFilename());
					mf.transferTo(file);
				} else {
					fileList = null;
				}
			}
		}
		return fileList;
	}
}