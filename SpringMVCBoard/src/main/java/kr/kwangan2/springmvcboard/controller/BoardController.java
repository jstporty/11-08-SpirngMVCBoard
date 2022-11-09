package kr.kwangan2.springmvcboard.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.kwangan2.springmvcboard.domain.AttachFileDTO;
import kr.kwangan2.springmvcboard.domain.BoardVO;
import kr.kwangan2.springmvcboard.domain.Criteria;
import kr.kwangan2.springmvcboard.service.BoardService;
import kr.kwangan2.springmvcboard.util.PageCalc;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
   
   private BoardService boardService;
   /*
   @GetMapping("/list")
   public String listBoardVO(Model model) {
      model.addAttribute("boardVOList", boardService.boardVOList());
      return "boardList";
   }
   */
   @GetMapping("/list")
   public String listBoardVOList(Criteria criteria, Model model) {
      model.addAttribute("boardVOList", boardService.boardList(criteria));
      model.addAttribute("pageCalc", new PageCalc(criteria, boardService.boardVOListCount(criteria)).calcPage());
      return "boardList";
   }
   
   @GetMapping("/boardInsert")
   public String boardInsert() {
      return "boardInsert";
   }
   
   @PostMapping("/boardInsertProc")
   public String boardInsertProc
   (BoardVO boardVO, RedirectAttributes redirectAttributes) {
      if(boardService.insertBoardVO(boardVO) > 0) {
         redirectAttributes.addFlashAttribute("result", "success");   
      }
      return "redirect:/";
   }
   
   @GetMapping("/select")
   public String selectBoardVO(
      @RequestParam("bno") Long bno,   Model model) {
      model.addAttribute("boardVO", boardService.selectBoardVO(bno));
      return "boardUpdate";
   }
   
   @PostMapping("/updateProc")
   public String updateBoardVO(BoardVO boarVO, RedirectAttributes rttr) {
      if(boardService.updateBoardVO(boarVO) > 0) {
         rttr.addFlashAttribute("result", "success");
      }
      return "redirect:/";
   }
   
   @GetMapping("/delete")
   public String deleteBoardVO
   (@RequestParam("bno") Long bno, RedirectAttributes rttr) { 
      if(boardService.deleteBoardVO(bno)>0) {
      rttr.addFlashAttribute("result", "success");
      }
   return "redirect:/";
}
   
   @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();

		String uploadFolder = "C:/upload";

		String uploadFolderPath = getFolder();

		File uploadPath = new File(uploadFolder, uploadFolderPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {

			AttachFileDTO attachDTO = new AttachFileDTO();

			log.info("-----------------------------------");

			String originalFileName = multipartFile.getOriginalFilename();
			attachDTO.setFileName(originalFileName);
			log.info("upload file name:" + originalFileName);
			log.info("upload file name:" + multipartFile.getSize());
			log.info("upload file name:" + multipartFile.isEmpty());
			log.info("upload file name:" + multipartFile.getName());
			try {
				log.info("upload file name:" + multipartFile.getBytes());

				log.info("upload file name:" + multipartFile.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("-----------------------------------");

			originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);

			UUID uuid = UUID.randomUUID();

			originalFileName = uuid.toString() + "_" + originalFileName;

			try {
				File saveFile = new File(uploadPath, originalFileName); // 유니크한 이름
				// File saveFile = new File(uploadPath, originalFileName); //원래 파일이름 ㅅㄱ ㅋ
				multipartFile.transferTo(saveFile);

				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);

				if (checkImageType(saveFile)) {

					attachDTO.setImage(true);

					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "thumb_" + originalFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

					thumbnail.close();

				}

				list.add(attachDTO);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// String uploadFileName=multipartFile.getOriginalFilename();

		} // for

		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);

	}
   

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);

	}

	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return (contentType != null ? contentType.startsWith("image") : false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName:" + fileName);

		File file = new File("C:\\upload\\" + fileName);

		log.info("file:" + file);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Contenst=type", Files.probeContentType(file.toPath()));

			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("user-Agent"	) String userAgent, String fileName) {
		log.info("donseload file: " + fileName);

		Resource resource = new FileSystemResource("C:/upload/" + fileName);
		
		if(resource.exists()==false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String resourceName= resource.getFilename();
		//remove UUID
		String resourceOriginalName=resourceName.substring(resourceName.indexOf("_")+1);
		
		
		HttpHeaders headers=new HttpHeaders();
		
	try {
		
		String downloadName=null;
		 if(userAgent.contains("Trident")) {
			 
			 log.info("IE browser");
			 downloadName=URLEncoder.encode(resourceName,"UTF-8").replace("/+" ," ");
		 }else if (userAgent.contains("Edge")) {
			 log.info("dege browser");
			  
			 downloadName=URLEncoder.encode(resourceName, "UTF-8");
			 log.info("edge name:" + downloadName);
		 }else {
			 log.info("chrome browser"	);
			 downloadName=new String(resourceName.getBytes("UTF-8"),"ISO_8859-1");
		 }
		
		headers.add("content=disposition","attachment; filename="+downloadName);
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
		return new ResponseEntity<Resource>	(resource, headers, HttpStatus.OK);
		
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {

		log.info("deleteFile: " + fileName);

		File file;

		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			if (type.equals("image")) {

				String largeFileName = file.getAbsolutePath().replace("thumb_", "");

				log.info("largeFileName: " + largeFileName);

				file = new File(largeFileName);

				file.delete();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}
   
}//class