Public Class Form2
    Private Sub ReferenceToolStripMenuItem_Click(sender As Object, e As EventArgs) Handles ReferenceToolStripMenuItem.Click
        MessageBox.Show("Created by Suraya Malek")
    End Sub

    Private Sub btnExit_Click(sender As Object, e As EventArgs) Handles btnExit.Click
        Me.Close()
    End Sub

    Private Sub btnCalculate_Click(sender As Object, e As EventArgs) Handles btnCalculate.Click
        Try
            Dim name As String
            name = UCase(txtName.Text)

            Dim gender As String
            gender = cbGender.Text

            Dim phone As String
            phone = txtPhoneNum.Text

            Dim course As String
            course = cbCourse.Text

            Dim semester As String
            semester = txtSemester.Text

            Dim fee As String
            fee = txtFee.Text

            Dim total As String
            total = txtFee.Text * txtSemester.Text

            txtDisplay.Text = "Name : " + name.ToString + vbNewLine + "Gender : " +
            gender.ToString + vbNewLine + "Phone : " + phone.ToString + vbNewLine + "Course : " +
            course.ToString + vbNewLine + "Semester : " + semester.ToString + vbNewLine + "Fee : RM" +
            total.ToString

        Catch ex As Exception

        End Try
        MsgBox("Error. Please fill up all details.")

    End Sub

    Private Sub Form2_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Call Connection()
    End Sub

    Private Sub btnEnter_Click(sender As Object, e As EventArgs) Handles btnEnter.Click
        Try
            cm = New OleDb.OleDbCommand
            With cm
                .Connection = cn
                .CommandType = CommandType.Text
                .CommandText = "INSERT INTO Student (Name,Gender,PhoneNum,Course,Semester,Fee) VALUES
                (@Name,@Gender,@PhoneNum,@Course,@Semester,@Fee)"

                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@Name", System.Data.OleDb.OleDbType.VarChar, 255,
                  Me.txtName.Text))
                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@Gender", System.Data.OleDb.OleDbType.VarChar, 255,
                Me.cbGender.Text))
                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@PhoneNum", System.Data.OleDb.OleDbType.VarChar, 255,
               Me.txtPhoneNum.Text))
                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@Course", System.Data.OleDb.OleDbType.VarChar, 255,
               Me.cbCourse.Text))
                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@Semester", System.Data.OleDb.OleDbType.VarChar, 255,
               Me.txtSemester.Text))
                .Parameters.Add(New System.Data.OleDb.OleDbParameter("@Fee", System.Data.OleDb.OleDbType.VarChar, 255,
               Me.txtFee.Text))

                cm.Parameters("@Name").Value = Me.txtName.Text
                cm.Parameters("@Gender").Value = Me.cbGender.Text
                cm.Parameters("@PhoneNum").Value = Me.txtPhoneNum.Text
                cm.Parameters("@Course").Value = Me.cbCourse.Text
                cm.Parameters("@Semester").Value = Me.txtSemester.Text
                cm.Parameters("@Fee").Value = Me.txtFee.Text

                cm.ExecuteNonQuery()
                MsgBox("Record Saved.", MsgBoxStyle.Information)
                Me.txtName.Text = ""
                Me.cbGender.Text = ""
                Me.txtPhoneNum.Text = ""
                Me.cbCourse.Text = ""
                Me.txtSemester.Text = ""
                Me.txtFee.Text = ""
                Exit Sub
            End With
        Catch ex As Exception
            MsgBox(ex.Message, MsgBoxStyle.Critical)
        End Try
    End Sub
End Class