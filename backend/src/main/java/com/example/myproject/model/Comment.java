package com.example.myproject.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Comment {
	private String commentId;
	private String docId;
	private String userId;
	private String content;
	private Date commentDate;
	
	public Comment(String commentId,String docId,String userId,String content,Date commentDate) {
		this.commentId = commentId; this.docId = docId; this.userId = userId; this.content = content;
		this.commentDate = commentDate;
	}
	
	/**
	 * 查找某个文档的评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Comment> findCommentByDocId(String id) throws Exception{
		String sql = "SELECT * FROM Comment WHERE DocId = '"+id+"'";
		ArrayList<Comment> res = new ArrayList<>();
		ResultSet rs = Repository.getInstance().doSqlSelectStatement(sql);
		while(rs.next()) {
			Comment c = new Comment(rs.getString("CommentId"), rs.getString("DocId"), rs.getString("UserId"), 
					rs.getString("Content"), rs.getDate("CommentDate"));
			res.add(c);
		}
		return res;
	}
	
	/**
	 * 查找某个用户发表的评论，按照时间由近到远排序
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Comment> findCommentByUserId(String id) throws Exception{
		String sql = "SELECT * FROM Comment WHERE UserId = '"+id+"' ORDER BY CommentDate DESC";
		ArrayList<Comment> res = new ArrayList<>();
		ResultSet rs = Repository.getInstance().doSqlSelectStatement(sql);
		while(rs.next()) {
			Comment c = new Comment(rs.getString("CommentId"), rs.getString("DocId"), rs.getString("UserId"), 
					rs.getString("Content"), rs.getDate("CommentDate"));
			res.add(c);
		}
		return res;
	}
	/**
	 * 根据id查找某个文档的评论
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	public static Comment findCommentByCommentId(String commentId) throws Exception{
		String sql = "SELECT * FROM Comment WHERE CommentId = '"+commentId+"'";
		ArrayList<Comment> res = new ArrayList<>();
		ResultSet rs = Repository.getInstance().doSqlSelectStatement(sql);
		while(rs.next()) {
			Comment c = new Comment(rs.getString("CommentId"), rs.getString("DocId"), rs.getString("UserId"), 
					rs.getString("Content"), rs.getDate("CommentDate"));
			res.add(c);
		}
		if(res.size()!=0)
				return res.get(0);
		else
			return null;
	}
	public static boolean addComment(Comment c) {
		String sql = "INSERT INTO Comment(CommentId,DocId,UserId,Content,CommentDate) VALUES" + c.toTupleInString();
		return Repository.getInstance().doSqlUpdateStatement(sql);
	}
	
	public static boolean deleteComment(Comment c) {
		String sql = "DELETE FROM Comment WHERE CommentId ='" 
				+ c.getCommentId()+"'";
		return Repository.getInstance().doSqlUpdateStatement(sql);
	}
	
	
	/**
	 * 获取下一个评论id
	 * @return
	 */
	public static String getNextId() {
		try {
			ResultSet rs = Repository.getInstance().doSqlSelectStatement("SELECT TOP 1 CommentId FROM Comment " + 
					"ORDER BY convert(int,CommentId) DESC;");
			if(rs.next()) {
				return String.valueOf(Integer.parseInt(rs.getString("CommentId")) + 1);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public String toTupleInString() {
		return "('"+commentId+"','"+docId+"','"+userId+"','"+content+"','"+new Timestamp(commentDate.getTime())+"')";
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	
	
}
